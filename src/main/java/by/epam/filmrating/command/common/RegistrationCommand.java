package by.epam.filmrating.command.common;

import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.UserService;
import by.epam.filmrating.util.RegistrationUtil;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class RegistrationCommand implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_LOGIN = "login";
    private final static String PARAM_PASSWORD = "password";
    private final static String PARAM_MESSAGE_REGISTRATION = "messageRegistration";
    private final static String ERROR_REGISTRATION = "error.registration";
    private final static String ERROR_REGISTRATION_FILLING = "error.registration.filling";
    private final static String SUCCESSFUL_REGISTRATION = "successful.registration";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";

    private UserService userService;

    public RegistrationCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        if (RegistrationUtil.checkLogin(login) && RegistrationUtil.checkPassword(password)) {
            password = DigestUtils.shaHex(password);
            if (RegistrationUtil.checkLogin(login, userService)) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                try {
                    userService.create(user);
                } catch (ServiceException e) {
                    request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
                    return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
                }
                request.setAttribute(PARAM_MESSAGE_REGISTRATION, TextManager.getProperty(SUCCESSFUL_REGISTRATION, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
                return "/controller?command=OPEN_LOGIN_PAGE";
            } else {
                request.setAttribute(PARAM_MESSAGE_REGISTRATION, TextManager.getProperty(ERROR_REGISTRATION, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
                return "/controller?command=OPEN_LOGIN_PAGE";
            }
        } else {
            request.setAttribute(PARAM_MESSAGE_REGISTRATION, TextManager.getProperty(ERROR_REGISTRATION_FILLING, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return "/controller?command=OPEN_LOGIN_PAGE";
        }
    }
}
