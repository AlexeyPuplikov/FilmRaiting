package by.epam.filmrating.command.client;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class ViewFilmsCommand implements IActionCommand {
    private final static String PATH_MAIN_USER_PAGE = "path.page.mainUser";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PATH_500_PAGE = "path.page.500Error";
    private final static String PARAM_FILMS = "films";
    private final static String PARAM_LIMIT_FILMS = "limitFilms";
    private final static String PARAM_PAGE = "page";
    private final static String PARAM_RECORDS_PER_PAGE = "recordsPerPage";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String IMAGE_DIRECTORY = "path.image";

    private FilmService filmService;

    public ViewFilmsCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter(PARAM_PAGE));
        int recordsPerPage = Integer.parseInt(request.getServletContext().getInitParameter(PARAM_RECORDS_PER_PAGE));
        try {
            List<Film> films = filmService.findAll();
            if (page > 0 && recordsPerPage != 0 && page <= (int) Math.ceil(films.size() * 1.0 / recordsPerPage)) {
                List<Film> limitFilms = filmService.findLimitFilms((page - 1) * recordsPerPage, recordsPerPage);
                for (Film film : films) {
                    String imageName = request.getServletContext().getRealPath("") + File.separator + ConfigurationManager.getProperty(IMAGE_DIRECTORY) + film.getName() + ".jpg";
                    if (!new File(imageName).exists() && film.getCover() != null) {
                        filmService.loadCoverToFile(film, imageName);
                    }
                }
                request.setAttribute(PARAM_FILMS, films);
                request.setAttribute(PARAM_LIMIT_FILMS, limitFilms);
                return ConfigurationManager.getProperty(PATH_MAIN_USER_PAGE);
            } else {
                return ConfigurationManager.getProperty(PATH_500_PAGE); //another error
            }
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
    }
}
