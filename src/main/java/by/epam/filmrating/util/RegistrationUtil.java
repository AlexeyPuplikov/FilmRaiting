package by.epam.filmrating.util;

import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.UserService;

import java.util.List;

public class RegistrationUtil {
    private final static String PATTERN_LOGIN = "[A-z0-9]{5,15}";
    private final static String PATTERN_PASSWORD = "[A-z0-9]{5,}";

    public boolean checkLogin(String login) {
        return !login.isEmpty() && login.matches(PATTERN_LOGIN);
    }

    public boolean checkLogin(String login, UserService userService) {
        List<User> users;
        boolean check = true;
        try {
            users = userService.findAll();
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

    public boolean checkPassword(String password) {
        return !password.isEmpty() && password.matches(PATTERN_PASSWORD);
    }

}
