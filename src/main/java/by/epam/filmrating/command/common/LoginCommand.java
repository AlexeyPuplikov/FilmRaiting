package by.epam.filmrating.command.common;

import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LoginCommand implements IActionCommand {
    private final static String PATH_INDEX_PAGE = "path.page.index";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_LOGIN = "login";
    private final static String PARAM_PASSWORD = "password";
    private final static String PARAM_USER = "user";
    private final static String PARAM_ADMIN = "admin";
    private final static String PARAM_STATUS = "status";
    private final static String PARAM_LOCALE = "locale";
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
        String login = request.getParameter(PARAM_LOGIN);
        String password = DigestUtils.shaHex(request.getParameter(PARAM_PASSWORD));
        User user = null;
        try {
            if (!login.isEmpty() && !password.isEmpty()) {
                user = userService.findUserByLoginAndPassword(login, password);
            }
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
        if (user != null && user.getRole() == EnumRole.USER) {
            request.getSession().setAttribute(PARAM_USER, user);
            request.getSession().setAttribute(PARAM_STATUS, EnumStatus.valueOf(user.getStatus()).getName());
            return ConfigurationManager.getProperty(PATH_INDEX_PAGE);
        } else {
            if (user != null && user.getRole() == EnumRole.ADMIN) {
                request.getSession().setAttribute(PARAM_ADMIN, user);
                return "/controller?command=OPEN_MAIN_ADMIN_PAGE";
            }
            request.setAttribute(PARAM_MESSAGE_LOGIN, TextManager.getProperty(ERROR_LOGIN, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return "/controller?command=OPEN_LOGIN_PAGE";
        }
    }
}
