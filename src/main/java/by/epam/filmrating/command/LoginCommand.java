package by.epam.filmrating.command;

import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {
    private final static String PATH_INDEX_PAGE = "path.page.index";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_LOGIN = "login";
    private final static String PARAM_PASSWORD = "password";
    private final static String PARAM_USER = "user";
    private final static String PARAM_ADMIN = "admin";
    private final static String PARAM_STATUS = "status";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_LANGUAGE = "language";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_MESSAGE_LOGIN = "messageLogin";
    private final static String ERROR_LOGIN = "error.login";

    private UserService userService;

    public LoginCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        HttpSession httpSession = request.getSession();
        String login = request.getParameter(PARAM_LOGIN);
        String password = DigestUtils.shaHex(request.getParameter(PARAM_PASSWORD));
        User user = null;
        try {
            user = userService.findUserByLoginAndPassword(login, password);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, SERVICE_ERROR);
            configurationManager.getProperty(PATH_ERROR_PAGE);
        }
        if (user != null && user.getRole() == EnumRole.USER) {
            httpSession.setAttribute(PARAM_USER, user);
            httpSession.setAttribute(PARAM_STATUS, EnumStatus.valueOf(user.getStatus()).getName());
            String currLocale = (String) httpSession.getAttribute(PARAM_LANGUAGE);
            request.setAttribute(PARAM_LOCALE, currLocale);
            return configurationManager.getProperty(PATH_INDEX_PAGE);
        } else {
            if (user != null && user.getRole() == EnumRole.ADMIN) {
                httpSession.setAttribute(PARAM_ADMIN, user);
                String currLocale = (String) httpSession.getAttribute(PARAM_LANGUAGE);
                request.setAttribute(PARAM_LOCALE, currLocale);
                return "/controller?command=OPEN_MAIN_ADMIN_PAGE";
            }
            request.setAttribute(PARAM_MESSAGE_LOGIN, configurationManager.getProperty(ERROR_LOGIN));
            return "/controller?command=OPEN_LOGIN_PAGE";
        }
    }
}
