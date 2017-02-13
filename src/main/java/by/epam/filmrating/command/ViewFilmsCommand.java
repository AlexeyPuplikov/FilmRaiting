package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public class ViewFilmsCommand implements ActionCommand {
    private final static String PATH_MAIN_USER_PAGE = "path.page.mainUser";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_FILMS = "films";
    private final static String PARAM_LIMIT_FILMS = "limitFilms";
    private final static String PARAM_PAGE = "page";
    private final static String PARAM_RECORDS_PER_PAGE = "recordsPerPage";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_LANGUAGE = "language";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String IMAGE_DIRECTORY = "C:\\Users\\user\\Desktop\\FinalProject\\FilmRaiting\\src\\main\\webapp\\resources\\images\\";

    private FilmService filmService;

    public ViewFilmsCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        int page = Integer.parseInt(request.getParameter(PARAM_PAGE));
        int recordsPerPage = Integer.parseInt(request.getParameter(PARAM_RECORDS_PER_PAGE));
        String imageName;
        List<Film> films = null;
        List<Film> limitFilms = null;
        try {
            limitFilms = filmService.findLimitFilms((page - 1) * recordsPerPage, recordsPerPage);
            films = filmService.findAll();
            for (Film film : films) {
                imageName = IMAGE_DIRECTORY + film.getName() + ".jpg";
                if (!new File(imageName).exists() && film.getCover() != null) {
                    filmService.loadCoverToFile(film, imageName);
                }
            }
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            configurationManager.getProperty(PATH_ERROR_PAGE);
        }

        request.setAttribute(PARAM_FILMS, films);
        String currLocale = (String) request.getSession().getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);
        request.setAttribute(PARAM_LIMIT_FILMS, limitFilms);

        return configurationManager.getProperty(PATH_MAIN_USER_PAGE);
    }
}
