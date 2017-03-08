package by.epam.filmrating.command.client;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class FindFilmCommand implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_ERROR_SEARCH = "errorSearch";
    private final static String PARAM_FILM = "filmName";
    private final static String PARAM_LOCALE = "locale";
    private final static String ERROR_SEARCH = "error.search";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";

    private FilmService filmService;

    public FindFilmCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(PARAM_FILM);
        Film film;
        try {
            film = filmService.findEntityBySign(name);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
        if (film != null) {
            return "/controller?command=VIEW_FILM&filmId=" + film.getFilmId();
        } else {
            request.setAttribute(PARAM_ERROR_SEARCH, TextManager.getProperty(ERROR_SEARCH, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return "/controller?command=VIEW_FILMS&page=1";
        }
    }
}
