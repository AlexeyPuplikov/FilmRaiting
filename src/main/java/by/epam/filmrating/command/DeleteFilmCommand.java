package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteFilmCommand implements ActionCommand {
    private final static String FILM_PARAM = "film";
    private final static String PATH_ADMIN_PAGE = "path.page.admin";
    private final static String PARAM_FILMS = "films";
    private final static String SUCCESSFUL_DELETE = "successfulDelete";

    private final static String SUCCESSFUL_TEXT = "Удаление произведено успешно";

    private FilmService filmService;

    public DeleteFilmCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String filmId = request.getParameter(FILM_PARAM);
        List<Film> films = null;
        try {
            filmService.delete(Integer.parseInt(filmId));
            films = filmService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute(PARAM_FILMS, films);
        request.setAttribute(SUCCESSFUL_DELETE, SUCCESSFUL_TEXT);
        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_ADMIN_PAGE);
    }
}
