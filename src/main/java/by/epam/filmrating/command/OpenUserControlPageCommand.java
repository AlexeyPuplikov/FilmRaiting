package by.epam.filmrating.command;

import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenUserControlPageCommand implements ActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PATH_PAGE_USER_CONTROL = "path.page.userControl";
    private final static String PARAM_USERS = "users";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_LANGUAGE = "language";
    private final static String PARAM_SUCCESSFUL = "successfulUpdate";
    private final static String SUCCESSFUL_TEXT = "successful.update";

    private UserService userService;

    public OpenUserControlPageCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        try {
            List<User> users = userService.findAll();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getRole() == EnumRole.ADMIN) {
                    users.remove(i);
                }
            }
            request.setAttribute(PARAM_USERS, users);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        }
        String currLocale = (String) request.getSession().getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);
        if (request.getParameter("successfulUpdate") != null) {
            request.setAttribute(PARAM_SUCCESSFUL, configurationManager.getProperty(SUCCESSFUL_TEXT));
        }
        return configurationManager.getProperty(PATH_PAGE_USER_CONTROL);
    }
}
