package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.StageDirectorService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenAddFilmPageCommandI implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PATH_ADD_FILM_PAGE = "path.page.addFilm";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_LANGUAGE = "language";
    private final static String PARAM_ALL_STAGE_DIRECTORS = "allStageDirectors";
    private final static String PARAM_ALL_FILMS = "allFilms";
    private final static String ADD_ERROR = "error.addFilm";
    private final static String ADD_PARAMETERS_ERROR = "error.addParameters";
    private final static String ADD_SUCCESSFUL = "successful.add";
    private final static String ADD_PARAMETER_SUCCESSFUL = "successful.addParameters";
    private final static String ADD_COVER_SUCCESSFUL = "successful.addCover";
    private final static String PARAM_ADD_MESSAGE = "messageAddFilm";

    private FilmService filmService;
    private StageDirectorService stageDirectorService;

    public OpenAddFilmPageCommandI() {
        this.filmService = new FilmService();
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        try {
            List<Film> films = filmService.findAll();
            List<StageDirector> stageDirectors = stageDirectorService.findAll();
            String currLocale = (String) request.getSession().getAttribute(PARAM_LANGUAGE);
            request.setAttribute(PARAM_LOCALE, currLocale);
            request.setAttribute(PARAM_ALL_STAGE_DIRECTORS, stageDirectors);
            request.setAttribute(PARAM_ALL_FILMS, films);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        }
        /*if (request.getParameter("errorAdd") != null) {
            request.setAttribute(PARAM_ADD_MESSAGE, textManager.getProperty(ADD_ERROR));
        }
        if (request.getParameter("successfulAdd") != null) {
            request.setAttribute(PARAM_ADD_MESSAGE, textManager.getProperty(ADD_SUCCESSFUL));
        }
        if (request.getParameter("successfulAddParameters") != null) {
            request.setAttribute(PARAM_ADD_MESSAGE, textManager.getProperty(ADD_PARAMETER_SUCCESSFUL));
        }
        if (request.getParameter("successfulAddCover") != null) {
            request.setAttribute(PARAM_ADD_MESSAGE, textManager.getProperty(ADD_COVER_SUCCESSFUL));
        }
        if (request.getParameter("errorAddParameters") != null) {
            request.setAttribute(PARAM_ADD_MESSAGE, textManager.getProperty(ADD_PARAMETERS_ERROR));
        }*/
        return configurationManager.getProperty(PATH_ADD_FILM_PAGE);
    }
}
