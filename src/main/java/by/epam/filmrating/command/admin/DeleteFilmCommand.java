package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Locale;

public class DeleteFilmCommand implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_FILM_ID = "film";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String PARAM_LOCALE = "locale";
    private final static String SERVICE_ERROR = "error.service";
    private final static String ADD_PARAMETERS_SUCCESSFUL = "successfulDelete";
    private final static String IMAGE_DIRECTORY = "path.image";

    private FilmService filmService;

    public DeleteFilmCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int filmId = Integer.parseInt(request.getParameter(PARAM_FILM_ID));
        try {
            Film film = filmService.findEntityBySign(filmId);
            String imageName = request.getServletContext().getRealPath("") + File.separator + ConfigurationManager.getProperty(IMAGE_DIRECTORY) + film.getName() + ".jpg";
            if (new File(imageName).exists()) {
                new File(imageName).delete();
            }
            filmService.delete(filmId);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
        return "redirect:/controller?command=OPEN_MAIN_ADMIN_PAGE&successfulDelete=" + ADD_PARAMETERS_SUCCESSFUL;
    }
}
