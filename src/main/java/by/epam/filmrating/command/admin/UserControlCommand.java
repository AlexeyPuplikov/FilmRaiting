package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class UserControlCommand implements IActionCommand {
    private final static String PARAM_USER = "user";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_LOCALE = "locale";
    private final static String SUCCESSFUL_UPDATE = "successfulUpdate";

    private UserService userService;

    public UserControlCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter(PARAM_USER));
        try {
            User currentUser = userService.findEntityBySign(userId);
            if (!currentUser.getBlocked()) {
                userService.updateIsBlocked(currentUser.getUserId(), true);
            } else {
                userService.updateIsBlocked(currentUser.getUserId(), false);
            }
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
        return "redirect:/controller?command=OPEN_USER_CONTROL_PAGE&successfulUpdate=" + SUCCESSFUL_UPDATE;
    }
}
