package by.epam.filmrating.command.common;

import by.epam.filmrating.manager.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;

public class OpenLoginPageCommand implements IActionCommand {
    private final static String PATH_LOGIN_PAGE = "path.page.login";

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty(PATH_LOGIN_PAGE);
    }
}
