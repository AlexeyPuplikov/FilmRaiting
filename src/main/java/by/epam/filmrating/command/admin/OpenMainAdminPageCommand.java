package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

public class OpenMainAdminPageCommand implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_FILMS = "films";
    private final static String PATH_ADMIN_PAGE = "path.page.admin";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_SUCCESSFUL_DELETE = "successfulDelete";
    private final static String PARAM_MESSAGE = "message";
    private final static String SUCCESSFUL_DELETE = "successful.delete";

    private FilmService filmService;

    public OpenMainAdminPageCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Film> films;
        try {
            films = filmService.findAll();
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
        request.setAttribute(PARAM_FILMS, films);
        if (request.getParameter(PARAM_SUCCESSFUL_DELETE) != null) {
            request.setAttribute(PARAM_MESSAGE, TextManager.getProperty(SUCCESSFUL_DELETE, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
        }
        return ConfigurationManager.getProperty(PATH_ADMIN_PAGE);
    }
}
