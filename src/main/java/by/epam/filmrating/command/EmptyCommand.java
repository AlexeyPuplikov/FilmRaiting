package by.epam.filmrating.command;

import by.epam.filmrating.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EmptyCommand implements ActionCommand {
    private final static String PATH_INDEX_PAGE = "path.page.index";
    private static final String PARAM_LOCALE = "locale";
    private static final String DEFAULT_LOCALE = "en_US";
    private static final String PARAM_LANGUAGE = "language";

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        HttpSession session = request.getSession(true);

        if (session.getAttribute(PARAM_LANGUAGE) == null) {
            session.setAttribute(PARAM_LANGUAGE, DEFAULT_LOCALE);
        }

        String currLanguage = (String) session.getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLanguage);

        return configurationManager.getProperty(PATH_INDEX_PAGE);
    }
}
