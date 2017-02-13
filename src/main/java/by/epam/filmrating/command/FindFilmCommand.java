package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;

public class FindFilmCommand implements ActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_ERROR_SEARCH = "errorSearch";
    private final static String ERROR_SEARCH = "error.search";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";

    private FilmService filmService;

    public FindFilmCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String name = request.getParameter("filmName");
        Film film = null;
        try {
            film = filmService.findEntityBySign(name);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            configurationManager.getProperty(PATH_ERROR_PAGE);
        }
        if (film != null && film.getFilmId() != 0) {
            return "/controller?command=VIEW_FILM&filmId=" + film.getFilmId();
        } else {
            request.setAttribute(PARAM_ERROR_SEARCH, configurationManager.getProperty(ERROR_SEARCH));
            return "/controller?command=VIEW_FILMS&page=1&recordsPerPage=4";
        }
    }
}
