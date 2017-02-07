package by.epam.filmrating.command;

import by.epam.filmrating.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class OpenLoginPageCommand implements ActionCommand {
    private final static String PATH_LOGIN_PAGE = "path.page.login";

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_LOGIN_PAGE);
    }
}
