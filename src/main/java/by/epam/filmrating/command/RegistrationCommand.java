package by.epam.filmrating.command;

import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RegistrationCommand implements ActionCommand {
    private final static String PATH_INDEX_PAGE = "path.page.index";
    private final static String PATH_LOGIN_PAGE = "path.page.login";
    private final static String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_PASSWORD = "password";
    private final static String PARAM_ERROR_REGISTRATION = "errorRegistration";

    private final static String ERROR = "Такой пользователь уже существует";

    private UserService userService;

    public RegistrationCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        ConfigurationManager configurationManager = new ConfigurationManager();
        if (checkLogin(login)) {
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            try {
                userService.create(user);
                user = userService.findUserByLogin(login, password);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user);
            httpSession.setAttribute("status", EnumStatus.valueOf(user.getStatus()).getName());
            return configurationManager.getProperty(PATH_INDEX_PAGE);
        } else {
            request.setAttribute(PARAM_ERROR_REGISTRATION, ERROR);
            return configurationManager.getProperty(PATH_LOGIN_PAGE);
        }
    }

    private boolean checkLogin(String login) {
        List<User> users;
        boolean check = true;
        try {
            users = this.userService.findAll();
            for (User user : users) {
                if (user.getLogin().equalsIgnoreCase(login)) {
                    check = false;
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return check;
    }
}
