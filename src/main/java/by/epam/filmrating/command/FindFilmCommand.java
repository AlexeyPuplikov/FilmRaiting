package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;

public class FindFilmCommand implements ActionCommand {
    private final static String PATH_MAIN_USER_PAGE = "path.page.index";
    private final static String PARAM_ERROR_SEARCH = "errorSearch";

    private final static String ERROR = "Фильм не найден";

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
            e.printStackTrace();
        }

        if (film != null && film.getFilmId() != 0) {
            return "redirect:/controller?command=VIEW_FILM&filmId=" + film.getFilmId();
        } else {
            request.setAttribute(PARAM_ERROR_SEARCH, ERROR);
            return configurationManager.getProperty(PATH_MAIN_USER_PAGE);
        }
    }
}
