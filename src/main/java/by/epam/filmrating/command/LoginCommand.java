package by.epam.filmrating.command;

import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {
    private final static String PATH_INDEX_PAGE = "path.page.index";
    private final static String PATH_LOGIN_PAGE = "path.page.login";
    private final static String PATH_ADMIN_PAGE = "path.page.admin";
    private final static String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_LOCALE = "locale";
    private static final String PARAM_LANGUAGE = "language";
    private final static String PARAM_ERROR_LOGIN = "errorLogin";

    private final static String ERROR = "Проверьте правильность введенных данных или зарегистрируйтесь";

    private UserService userService;

    public LoginCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        ConfigurationManager configurationManager = new ConfigurationManager();
        User user = null;
        try {
            user = userService.findUserByLogin(login, password);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        HttpSession httpSession = request.getSession();
        if (user != null && user.getRole() == EnumRole.USER) {
            httpSession.setAttribute("user", user);
            String currLocale = (String) httpSession.getAttribute(PARAM_LANGUAGE);
            request.setAttribute(PARAM_LOCALE, currLocale);
            return configurationManager.getProperty(PATH_INDEX_PAGE);
        } else {
            if (user != null && user.getRole() == EnumRole.ADMIN) {
                return configurationManager.getProperty(PATH_ADMIN_PAGE);
            }
            request.setAttribute(PARAM_ERROR_LOGIN, ERROR);
            return configurationManager.getProperty(PATH_LOGIN_PAGE);
        }
    }
}
