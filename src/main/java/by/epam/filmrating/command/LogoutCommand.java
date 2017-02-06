package by.epam.filmrating.command;

import by.epam.filmrating.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    private final static String PATH_INDEX_PAGE = "path.page.index";

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        request.getSession().invalidate();
        return configurationManager.getProperty(PATH_INDEX_PAGE);
    }
}
