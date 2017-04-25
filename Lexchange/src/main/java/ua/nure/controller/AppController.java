package ua.nure.controller;

import org.joda.time.LocalDateTime;
import org.json.simple.JSONArray;
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
import ua.nure.model.Message;
import ua.nure.model.bean.SearchBean;
import ua.nure.model.enumerated.Role;
import ua.nure.service.AppUserService;
import ua.nure.service.ChatService;
import ua.nure.service.EmployeeService;
import ua.nure.service.MessageService;
import ua.nure.util.Sender;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    Sender sender;

    @Autowired
    MessageSource messageSource;

    @Autowired
    MessageService messageService;

    @Autowired
    ChatService chatService;


    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String openSearch(ModelMap model, HttpSession session, SearchBean searchBean) {
        AppUser user = (AppUser) session.getAttribute("user");
        if(user == null || user.getRole().name().equals("BLOCKED") || user.getRole().name().equals("NEW")) {
            model.addAttribute("appUser", new AppUser());
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
        session.removeAttribute("user");
        return "index";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }


    ////////////DO NOT DISTURB!!!!//////////////////////////////////////CHAT///////////////////////DO NOT DISTURB!!!!!!
    @RequestMapping(value = "/chatPage", method = RequestMethod.GET)
    public String getChatPage(){
        return "chat";
    }

    @RequestMapping(value = {"/leaveChat"}, method = RequestMethod.POST)
    public void updateChatOf2(@RequestParam long chatId) {
        chatService.updateChatStatus(chatId);
    }

    @RequestMapping(value = {"/createChatOf2"}, method = RequestMethod.POST)
    public void createChatOf2(@RequestParam long userId, HttpSession session) {
        Chat chat = new Chat();

        AppUser user1 = (AppUser) session.getAttribute("user");
        AppUser user2 = appUserService.findById(userId);
        List<AppUser> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        chat.setUsers(users);
        chat.setActive(true);

        chatService.createChat(chat);
    }

    @RequestMapping(value = {"/chat"}, method = RequestMethod.GET)
    @ResponseBody
    public List<String> getMessages(@RequestParam long chatId) {
        List<Message> messages = messageService.findAllMessagesForChat(chatId);
        List<String> stringMessages = new ArrayList<>();
        for (Message m: messages){
            String sb = m.getUser().getFirstName() + ": " +
                    m.getSendingTime().getYear() + "-" +
                    m.getSendingTime().getMonthOfYear() + "-" +
                    m.getSendingTime().getDayOfMonth() + " " +
                    m.getSendingTime().getHourOfDay() + ":" +
                    m.getSendingTime().getMinuteOfHour() + "    " +
                    m.getContent() + System.lineSeparator();
            stringMessages.add(sb);
        }
        return stringMessages;
    }

    @RequestMapping(value = {"/chat"}, method = RequestMethod.POST)
    @ResponseBody
    public void postMessage(@RequestParam String messageText, @RequestParam String chatId, HttpSession session) {
        Message message = new Message();

        message.setSendingTime(new LocalDateTime());
        message.setContent(messageText);
        message.setChat(chatService.findChatById(Long.valueOf(chatId)));
        message.setUser((AppUser) session.getAttribute("user"));

        messageService.createMessage(message);
    }

//    private final ChatRepository chatRepository = new InMemoryChatRepository();
//
//    private final Map<DeferredResult<List<String>>, Integer> chatRequests =
//            new ConcurrentHashMap<DeferredResult<List<String>>, Integer>();
//
//
//    @RequestMapping(value = "/chat", method = RequestMethod.GET)
//    @ResponseBody
//    public DeferredResult<List<String>> getMessages(@RequestParam int messageIndex) {
//
//        final DeferredResult<List<String>> deferredResult = new DeferredResult<List<String>>(null, Collections.emptyList());
//        this.chatRequests.put(deferredResult, messageIndex);
//
//        deferredResult.onCompletion(new Runnable() {
//            @Override
//            public void run() {
//                chatRequests.remove(deferredResult);
//            }
//        });
//
//        List<String> messages = this.chatRepository.getMessages(messageIndex);
//        if (!messages.isEmpty()) {
//            deferredResult.setResult(messages);
//        }
//
//        return deferredResult;
//    }
//
//    @RequestMapping(value = "/chat", method = RequestMethod.POST)
//    @ResponseBody
//    public void postMessage(@RequestParam String message) {
//
//        this.chatRepository.addMessage(message);
//
//        // Update all chat requests as part of the POST request
//        // See Redis branch for a more sophisticated, non-blocking approach
//
//        for (Map.Entry<DeferredResult<List<String>>, Integer> entry : this.chatRequests.entrySet()) {
//            List<String> messages = this.chatRepository.getMessages(entry.getValue());
//            entry.getKey().setResult(messages);
//        }
//    }

    ///////////////DO NOT DISTURB!!!!///////////////////////////////////CHAT///////////////////////DO NOT DISTURB!!!!!!


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
