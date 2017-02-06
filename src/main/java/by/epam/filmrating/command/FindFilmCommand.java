package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FindFilmCommand implements ActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.findError";
    private static final String PARAM_LOCALE = "locale";
    private static final String PARAM_LANGUAGE = "language";

    private FilmService filmService;

    public FindFilmCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String name = request.getParameter("name");
        Film film = null;
        try {
            film = filmService.findFilmByName(name);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession(true);
        String currLocale = (String) session.getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);

        if (film != null && film.getFilmId() != 0) {
            return "redirect:/controller?command=VIEW_FILM&filmId=" + film.getFilmId();
        } else {
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        }
    }
}
