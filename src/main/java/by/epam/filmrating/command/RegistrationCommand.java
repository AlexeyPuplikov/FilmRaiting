package by.epam.filmrating.command;

import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.UserService;
import by.epam.filmrating.util.RegistrationUtil;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
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
        ConfigurationManager configurationManager = new ConfigurationManager();
        RegistrationUtil registrationUtil = new RegistrationUtil();
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        if (registrationUtil.checkLogin(login) && registrationUtil.checkPassword(password)) {
            password = DigestUtils.shaHex(password);
            if (registrationUtil.checkLogin(login, userService)) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                try {
                    userService.create(user);
                } catch (ServiceException e) {
                    request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
                    configurationManager.getProperty(PATH_ERROR_PAGE);
                }
                request.setAttribute(PARAM_MESSAGE_REGISTRATION, configurationManager.getProperty(SUCCESSFUL_REGISTRATION));
                return "/controller?command=OPEN_LOGIN_PAGE";
            } else {
                request.setAttribute(PARAM_MESSAGE_REGISTRATION, configurationManager.getProperty(ERROR_REGISTRATION));
                return "/controller?command=OPEN_LOGIN_PAGE";
            }
        } else {
            request.setAttribute(PARAM_MESSAGE_REGISTRATION, configurationManager.getProperty(ERROR_REGISTRATION_FILLING));
            return "/controller?command=OPEN_LOGIN_PAGE";
        }
    }
}
