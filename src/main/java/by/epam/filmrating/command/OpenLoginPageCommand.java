package by.epam.filmrating.command;

import by.epam.filmrating.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class OpenLoginPageCommand implements ActionCommand {
    private final static String PATH_LOGIN_PAGE = "path.page.login";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_LANGUAGE = "language";

    @Override
    public String execute(HttpServletRequest request) {
        String currLocale = (String) request.getSession().getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);
        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_LOGIN_PAGE);
    }
}
