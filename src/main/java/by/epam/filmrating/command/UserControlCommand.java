package by.epam.filmrating.command;

import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserControlCommand implements ActionCommand {
    private final static String USER_PARAM = "user";
    private final static String PATH_USER_CONTROL_PAGE = "path.page.userControl";
    private final static String PARAM_USERS = "users";
    private final static String SUCCESSFUL_UPDATE = "successfulUpdate";

    private final static String SUCCESSFUL_TEXT = "Пользователь успешно заблокирован";

    private UserService userService;

    public UserControlCommand() {
        this.userService = new UserService();
    }
    @Override
    public String execute(HttpServletRequest request) {
        String userId = request.getParameter(USER_PARAM);
        List<User> users = new ArrayList<>();
        User currentUser;
        try {
            currentUser = userService.findEntityBySign(Integer.parseInt(userId));
            System.out.println(currentUser.getBlocked());
            if (!currentUser.getBlocked()) {
                userService.updateIsBlocked(currentUser.getUserId(), true);
            } else {
                userService.updateIsBlocked(currentUser.getUserId(), false);
            }
            users = userService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getRole() == EnumRole.ADMIN) {
                users.remove(i);
            }
        }
        request.setAttribute(PARAM_USERS, users);
        request.setAttribute(SUCCESSFUL_UPDATE, SUCCESSFUL_TEXT);
        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_USER_CONTROL_PAGE);
    }
}
