package ua.nure.controller;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.model.*;
import ua.nure.model.Dictionary;
import ua.nure.model.bean.SearchBean;
import ua.nure.model.enumerated.Role;
import ua.nure.service.*;
import ua.nure.util.Sender;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


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

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    WordService wordService;

    @Autowired
    ComplainService complainService;

    @RequestMapping(value = {"/forgive"}, method = RequestMethod.GET)
    public String forgiveUser(ModelMap model, HttpSession session, @RequestParam long id) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            return getIndex(model);
        }
        complainService.deleteComplainByUserId(id);
        return getAdminPage(model, session);
    }

    @RequestMapping(value = {"/punish"}, method = RequestMethod.GET)
    public String punishUser(ModelMap model, HttpSession session, @RequestParam long id) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            return getIndex(model);
        }
        complainService.deleteComplainByUserId(id);
        appUserService.blockById(id);
        return getAdminPage(model, session);
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String getAdminPage(ModelMap model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            return getIndex(model);
        }
        List<Complain> complains = complainService.findAllComplains();
        model.addAttribute("complains", complains);
        return "admin";
    }

    @RequestMapping(value = {"/leave"}, method = RequestMethod.GET)
    public String leave(ModelMap model, HttpSession session, @RequestParam long id) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (!validateRoles(user, model)) {
            return "cabinet";
        }

        Chat chat = chatService.findChatById(id);
        chat.setActive(false);
        chatService.updateChat(chat);
        return openChats(model, session);
    }

    @RequestMapping(value = {"/complain"}, method = RequestMethod.GET)
    public String complain(ModelMap model, HttpSession session, @RequestParam long id) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (!validateRoles(user, model)) {
            return "cabinet";
        }

        Chat chat = chatService.findChatById(id, true);
        for (AppUser member : chat.getUsers()) {
            if (member.getId() != user.getId()) {
                complainService.createComplain(new Complain(member));
            }
        }
        return openChats(model, session);
    }

    @RequestMapping(value = {"/decline"}, method = RequestMethod.GET)
    public String declineInvite(ModelMap model, HttpSession session, @RequestParam long id) {
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
        return openChats(model, session);
    }

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
        return "chats";
    }

    private Long createChat(Long... ids) {
        List<AppUser> users = Arrays.asList(ids).stream()
                .map(id -> appUserService.findById(id, true)).collect(Collectors.toList());
        Chat chat = new Chat(users);
        users.forEach(u -> u.getChats().add(chat));
        appUserService.updateUser(users.get(0));
        return chat.getId();
    }

    @RequestMapping(value = {"/chats"}, method = RequestMethod.GET)
    public String openChats(ModelMap model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (!validateRoles(user, model)) {
            return "cabinet";
        }
        List<Chat> chats = chatService.findAllChatsByUserId(user.getId(), true);
        Map<Chat, Message> chatsWithMessages = new LinkedHashMap<>();
        for (Chat chat : chats) {
            chat.getUsers().remove(user);
            List<Message> messages = messageService.findAllMessagesForChat(chat.getId());
            chatsWithMessages.put(chat, messages.isEmpty() ? null : messages.get(messages.size() - 1));
        }
        model.addAttribute("chats", chatsWithMessages);

        Map<Long, AppUser> invitations = new LinkedHashMap<>();
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

            session.setAttribute("user", appUserService.findById(appUser.getId()));
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
            appUserService.updateUser(user, Role.USER);
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
    public String logout(HttpSession session, ModelMap model) {
        session.invalidate();
        return getIndex(model);
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getIndex(ModelMap model) {
        Map<String, Long> regions = appUserService.countRegions();
        model.addAttribute("regions", regions);
        Long members = 0L;
        for (Map.Entry<String, Long> entry : regions.entrySet()) {
            members += entry.getValue();
        }
        model.addAttribute("regions", regions);
        model.addAttribute("members", members);
        return "index";
    }

    //////////////////////////////////DICTIONARY//////////////////////

    @RequestMapping(value = "/newComment", method = RequestMethod.POST)
    public void updateWordComment(@RequestParam long wordId, @RequestParam String comment,
                                  @RequestParam long dictionaryId, HttpSession session) {
        Word word = wordService.findWordById(wordId);
        Dictionary dictionary = dictionaryService.findDictionaryById(dictionaryId);
        wordService.updateWordComment(wordId, comment);

        AppUser user = (AppUser) (session.getAttribute("user"));

        int wordPosition = dictionary.getWords().indexOf(word);
        int dictionaryPosition = user.getDictionaries().indexOf(dictionary);

        dictionary.getWords().remove(wordPosition);
        user.getDictionaries().remove(dictionaryPosition);

        word.setComment(comment);

        dictionary.getWords().add(wordPosition, word);
        user.getDictionaries().add(dictionaryPosition, dictionary);
    }

    //////////////////////////////////DICTIONARY//////////////////////


    //////////////////////////////////CHAT//////////////////////
    @RequestMapping(value = "/enterChat", method = RequestMethod.GET)
    public String getChatPage(ModelMap model, HttpSession session, @RequestParam long chatId) {
        AppUser user = (AppUser) session.getAttribute("user");
        long userId = user.getId();
        List<AppUser> users = appUserService.findUsersOfChat(chatId);
        for (AppUser u : users) {
            if (u.getId() != userId) {
                model.put("learnedLanguage", u.getNativeLanguage());
            }
        }
        model.put("chatId", chatId);
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

    @RequestMapping(value = {"/newMessages"}, method = RequestMethod.GET)
    @ResponseBody
    public List<String> getNewMessages(@RequestParam long chatId, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        AppUserChat appUserChat = new AppUserChat(chatId, user.getId());
        ServletContext context = session.getServletContext();

        HashMap<AppUserChat, ArrayList<Message>> messageMap = (HashMap<AppUserChat, ArrayList<Message>>)
                context.getAttribute("chatUserMessages");
        List<Message> messages = messageMap.get(appUserChat);
        messageMap.put(appUserChat, new ArrayList<>());
        context.setAttribute("chatUserMessages", messageMap);

        return formatMessages(messages);
    }

    @RequestMapping(value = {"/chat"}, method = RequestMethod.GET)
    @ResponseBody
    public List<String> getMessages(@RequestParam String chatId, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        AppUserChat appUserChat = new AppUserChat(Long.valueOf(chatId), user.getId());
        ServletContext context = session.getServletContext();

        HashMap<AppUserChat, ArrayList<Message>> messageMap = (HashMap<AppUserChat, ArrayList<Message>>)
                context.getAttribute("chatUserMessages");
        messageMap.put(appUserChat, new ArrayList<>());
        context.setAttribute("chatUserMessages", messageMap);

        List<Message> messages = messageService.findAllMessagesForChat(Long.valueOf(chatId));
        return formatMessages(messages);
    }

    private List<String> formatMessages(List<Message> messages) {
        List<String> stringMessages = new ArrayList<>();
        for (Message m : messages) {
            String sb = m.getUser().getFirstName() + ": " +
                    m.getSendingTime().getYear() + "-" +
                    m.getSendingTime().getMonthOfYear() + "-" +
                    m.getSendingTime().getDayOfMonth() + " " +
                    m.getSendingTime().getHourOfDay() + ":" +
                    m.getSendingTime().getMinuteOfHour() + ":" +
                    m.getSendingTime().getSecondOfMinute() + "    " +
                    m.getContent() + System.lineSeparator();
            stringMessages.add(sb);
        }
        return stringMessages;
    }


    @RequestMapping(value = {"/chat"}, method = RequestMethod.POST)
    @ResponseBody
    public void postMessage(@RequestParam String messageText, @RequestParam long chatId, HttpSession session) {
        Message message = new Message();
        Chat chat = chatService.findChatById(chatId, true);

        message.setSendingTime(new LocalDateTime());
        message.setContent(messageText);
        message.setChat(chat);
        message.setUser((AppUser) session.getAttribute("user"));

        messageService.createMessage(message);
        setMessageToContextMap(session, message, chat);
    }

    @RequestMapping(value = {"/addNewWord"}, method = RequestMethod.POST)
    public void addNewWord(@RequestParam String translation, @RequestParam String value,
                           @RequestParam String language, HttpSession session) {
        Word word = new Word();
        word.setComment("comment");
        word.setTranslation(translation);
        word.setValue(value);

        AppUser user = (AppUser) session.getAttribute("user");
        for (Dictionary dic : user.getDictionaries()) {
            if (dic.getLanguage().equals(language)) {
                word.setDictionary(dic);
                dic.getWords().add(word);
            }
        }

        if (word.getDictionary() == null) {
            Dictionary dictionary = new Dictionary();
            dictionary.setLanguage(language);
            dictionary.setUser(user);
            word.setDictionary(dictionary);
            dictionary.getWords().add(word);
            user.getDictionaries().add(dictionary);
            appUserService.updateUser(user);
            dictionaryService.createDictionary(dictionary);
        }

        wordService.createWord(word);
    }

    private void setMessageToContextMap(HttpSession session, Message message, Chat chat) {
        ServletContext context = session.getServletContext();

        HashMap<AppUserChat, ArrayList<Message>> messageMap = (HashMap<AppUserChat, ArrayList<Message>>)
                context.getAttribute("chatUserMessages");
        List<AppUser> usersOfChat = chat.getUsers();

        for (AppUser us : usersOfChat) {
            AppUserChat appUserChat = new AppUserChat(chat.getId(), us.getId());
            ArrayList<Message> messageList = messageMap.get(appUserChat);
            if (messageList == null) {
                messageList = new ArrayList<>();
                messageMap.put(appUserChat, messageList);
            }
            messageList.add(message);
        }

        context.setAttribute("chatUserMessages", messageMap);
    }

    //////////////////////////////////CHAT//////////////////////

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {

        ModelAndView model = new ModelAndView("error/generic_error");
        model.addObject("errMsg", "this is Exception.class");
        //ToDo: test
        return model;
    }

    private boolean validateRoles(AppUser user, ModelMap model) {
        if (user == null || user.getRole().equals(Role.BLOCKED) || user.getRole().equals(Role.NEW)) {
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
