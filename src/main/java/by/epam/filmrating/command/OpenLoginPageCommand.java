package by.epam.filmrating.command;

import by.epam.filmrating.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OpenLoginPageCommand implements ActionCommand {
    private final static String PATH_LOGIN_PAGE = "path.page.login";

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_LOGIN_PAGE);
    }
}
