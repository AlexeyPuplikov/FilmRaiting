package by.epam.filmrating.command.common;

import by.epam.filmrating.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class ChangeLanguageCommand implements IActionCommand {
    private static final String PARAM_LOCALE = "locale";
    private final static String PATH_INDEX_PAGE = "path.page.index";

    @Override
    public String execute(HttpServletRequest request) {
        String localeName = request.getParameter(PARAM_LOCALE);
        Locale locale = new Locale(localeName);
        request.getSession().setAttribute(PARAM_LOCALE, locale);
        if (request.getSession().getAttribute("admin") != null) {
            return "/controller?command=OPEN_MAIN_ADMIN_PAGE";
        }
        return ConfigurationManager.getProperty(PATH_INDEX_PAGE);
    }
}
