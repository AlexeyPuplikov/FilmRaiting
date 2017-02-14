package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenMainAdminPageCommand implements ActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_FILMS = "films";
    private final static String PATH_ADMIN_PAGE = "path.page.admin";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_LANGUAGE = "language";
    private final static String PARAM_MESSAGE = "message";
    private final static String SUCCESSFUL_DELETE = "successful.delete";

    private FilmService filmService;

    public OpenMainAdminPageCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        List<Film> films = null;
        try {
            films = filmService.findAll();
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        }

        String currLocale = (String) request.getSession().getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);
        request.setAttribute(PARAM_FILMS, films);
        if (request.getParameter("successfulDelete") != null) {
            request.setAttribute(PARAM_MESSAGE, configurationManager.getProperty(SUCCESSFUL_DELETE));
        }
        return configurationManager.getProperty(PATH_ADMIN_PAGE);
    }
}
