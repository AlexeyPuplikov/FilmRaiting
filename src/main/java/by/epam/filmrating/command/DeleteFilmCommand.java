package by.epam.filmrating.command;

import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;

public class DeleteFilmCommand implements ActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_FILM_ID = "film";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";

    private FilmService filmService;

    public DeleteFilmCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        int filmId = Integer.parseInt(request.getParameter(PARAM_FILM_ID));
        try {
            filmService.delete(filmId);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, SERVICE_ERROR);
            configurationManager.getProperty(PATH_ERROR_PAGE);
        }
        return "redirect:/controller?command=OPEN_MAIN_ADMIN_PAGE";
    }
}
