package ua.nure.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.nure.model.AppUser;
import ua.nure.model.Chat;
import ua.nure.model.Employee;
import ua.nure.model.Invite;
import ua.nure.model.bean.SearchBean;
import ua.nure.model.enumerated.Role;
import ua.nure.service.AppUserService;
import ua.nure.service.ChatService;
import ua.nure.service.EmployeeService;
import ua.nure.util.Sender;


@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    ChatService chatService;

    @Autowired
    Sender sender;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = {"/accept"}, method = RequestMethod.GET)
    public String acceptInvite(ModelMap model, HttpSession session, @RequestParam long id) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (!validateRoles(user, model)) {
            return "cabinet";
        }

        Optional<Invite> optional = user.getInvites().stream()
                .filter(i -> i.getId() == id)
                .findFirst();
        if (!optional.isPresent()) {
            return "error";
        }
        Invite invite = optional.get();
        user.getInvites().remove(invite);
        appUserService.updateUser(user);
        createChat(user.getId(), invite.getFromUserId());
        return "chat"; //ToDo: replace with chat?id= method call
    }

    private Long createChat(Long... ids) {
        List<AppUser> users = Arrays.asList(ids).stream()
                .map(id -> appUserService.findById(id, true)).collect(Collectors.toList());
        Chat chat = new Chat(users);
        users.forEach(u -> u.getChats().add(chat));
        users.forEach(appUserService::updateUser);
        return chat.getId();
    }

    @RequestMapping(value = {"/chats"}, method = RequestMethod.GET)
    public String openChats(ModelMap model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (!validateRoles(user, model)) {
            return "cabinet";
        }
        List<Chat> chats = chatService.findAllChatsByUserId(user.getId(), true);
        for (Chat chat : chats) {
            chat.getUsers().remove(user);
        }
        model.addAttribute("chats", chats);

        Map<Long, AppUser> invitations = new HashMap<>();
        for (Invite invite : user.getInvites()) {
            long id = invite.getFromUserId();
            invitations.put(id, appUserService.findById(id));
        }
        model.addAttribute("invitations", invitations);

        return "chats";
    }

    @RequestMapping(value = {"/invite"}, method = RequestMethod.GET)
    public String invite(ModelMap model, HttpSession session, @RequestParam long id, SearchBean searchBean) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (!validateRoles(user, model)) {
            return "cabinet";
        }
        AppUser toUser = appUserService.findById(id);
        if (toUser != null && toUser.getId() != user.getId()
                && !toUser.haveInviteFrom(user.getId())) {
            toUser.getInvites().add(new Invite(user.getId(), toUser));
            appUserService.updateUser(toUser);
        }
        searchBean.setService(appUserService);
        searchBean.setCurrentUser(user);
        model.addAttribute("searchBean", searchBean);
        return "search";
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String openSearch(ModelMap model, HttpSession session, SearchBean searchBean) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (!validateRoles(user, model)) {
            return "cabinet";
        }
        searchBean.setService(appUserService);
        searchBean.setCurrentUser(user);
        model.addAttribute("searchBean", searchBean);

        return "search";
    }

    @RequestMapping(value = {"/cabinet"}, method = RequestMethod.GET)
    public String openCabinet(ModelMap model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) {
            user = new AppUser();
        }
        model.addAttribute("appUser", user);
        return "cabinet";
    }

    @RequestMapping(value = {"/cabinet"}, method = RequestMethod.POST)
    public String register(@Valid AppUser appUser, BindingResult result, HttpSession session) {
        if (!result.hasErrors()) {
            if (!appUser.getPassword().equals(appUser.getRePassword())) {
                FieldError error = new FieldError("appUser", "rePassword", messageSource.getMessage(
                        "wrong.rePassword", null, Locale.getDefault()));
                result.addError(error);
                return "cabinet";
            }

            if (appUserService.findUserByEmail(appUser.getEmail()) == null) {
                appUser.setRole(Role.NEW);
                try {
                    sendApprovement(appUser);
                } catch (MessagingException e) {
                    return "error";
                }
            }
            appUserService.createOrUpdate(appUser);

            session.setAttribute("user", appUser);
            ((List<Long>) session.getServletContext().getAttribute("online")).add(appUser.getId());
        }
        return "cabinet";
    }

    private void sendApprovement(AppUser appUser) throws MessagingException {
        String subject = "Lexchange registration";
        String code = generateUniqueCode();
        String text = "Please, follow <a href=\"http://localhost:8080/lexchange/approve-"
                + code + "-code\">this</a> link to confirm your e-mail";
        appUser.setApprovementCode(code);
        sender.send(subject, text, appUser.getEmail());
    }

    @RequestMapping(value = {"/approve-{code}-code"}, method = RequestMethod.GET)
    public String editEmployee(@PathVariable String code, HttpSession session, ModelMap model) {
        AppUser user = appUserService.findByApprovementCode(code);
        if (user != null) {
            user.setRole(Role.USER);
            appUserService.updateUser(user);
            session.setAttribute("user", user);
            ((List<Long>) session.getServletContext().getAttribute("online")).add(user.getId());
            model.addAttribute("appUser", user);
            return "cabinet";
        }
        return "index";
    }

    private String generateUniqueCode() {
        Random random = new Random();
        String temp = String.valueOf(random.nextInt());
        if (appUserService.findByApprovementCode(temp) == null) {
            return temp;
        } else {
            return generateUniqueCode();
        }
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        AppUser appUser = appUserService.findUserByEmail(email);
        if (appUser != null && appUser.getPassword().equals(password)) {
            session.setAttribute("user", appUser);
            ((List<Long>) session.getServletContext().getAttribute("online")).add(appUser.getId());
            return "index";
        } else {
            return "redirect: cabinet";
        }
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String getLogin() {
        return "index";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }


    private boolean validateRoles(AppUser user, ModelMap model) {
        if (user == null || user.getRole().name().equals("BLOCKED") || user.getRole().name().equals("NEW")) {
            model.addAttribute("appUser", new AppUser());
            return false;
        }
        return true;
    }
    /////////////////////////////////////////////////////////////----OLD----///----CONTROLLER----////////////////////////


    /*
     * This method will list all existing employees.
     */
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String listEmployees(ModelMap model) {

        List<Employee> employees = employeeService.findAllEmployees();
        model.addAttribute("employees", employees);
        return "allemployees";
    }

    /*
     * This method will provide the medium to add a new employee.
     */
    @RequestMapping(value = {"/new"}, method = RequestMethod.GET)
    public String newEmployee(ModelMap model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("edit", false);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving employee in database. It also validates the user input
     */
    @RequestMapping(value = {"/new"}, method = RequestMethod.POST)
    public String saveEmployee(@Valid Employee employee, BindingResult result,
                               ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

		/*
         * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation
		 * and applying it on field [ssn] of Model class [Employee].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
        if (!employeeService.isEmployeeSsnUnique(employee.getId(), employee.getSsn())) {
            FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
            result.addError(ssnError);
            return "registration";
        }

        employeeService.saveEmployee(employee);

        model.addAttribute("success", "Employee " + employee.getName() + " registered successfully");
        return "success";
    }


    /*
     * This method will provide the medium to update an existing employee.
     */
    @RequestMapping(value = {"/edit-{ssn}-employee"}, method = RequestMethod.GET)
    public String editEmployee(@PathVariable String ssn, ModelMap model) {
        Employee employee = employeeService.findEmployeeBySsn(ssn);
        model.addAttribute("employee", employee);
        model.addAttribute("edit", true);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating employee in database. It also validates the user input
     */
    @RequestMapping(value = {"/edit-{ssn}-employee"}, method = RequestMethod.POST)
    public String updateEmployee(@Valid Employee employee, BindingResult result,
                                 ModelMap model, @PathVariable String ssn) {

        if (result.hasErrors()) {
            return "registration";
        }

        if (!employeeService.isEmployeeSsnUnique(employee.getId(), employee.getSsn())) {
            FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
            result.addError(ssnError);
            return "registration";
        }

        employeeService.updateEmployee(employee);

        model.addAttribute("success", "Employee " + employee.getName() + " updated successfully");
        return "success";
    }


    /*
     * This method will delete an employee by it's SSN value.
     */
    @RequestMapping(value = {"/delete-{ssn}-employee"}, method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable String ssn) {
        employeeService.deleteEmployeeBySsn(ssn);
        return "redirect:/list";
    }

}
