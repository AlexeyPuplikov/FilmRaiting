package by.epam.filmrating.command;

import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class OpenUserControlPageCommand implements ActionCommand {
    private final static String PATH_PAGE_USER_CONTROL = "path.page.userControl";
    private final static String USER_PARAM = "users";

    private UserService userService;

    public OpenUserControlPageCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<User> users = new ArrayList<>();

        try {
            users = userService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getRole() == EnumRole.ADMIN) {
                users.remove(i);
            }
        }

        request.setAttribute(USER_PARAM, users);

        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_PAGE_USER_CONTROL);
    }
}
