package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FilmCommand implements ActionCommand {
    private final static String PATH_FILM_PAGE = "path.page.film";
    private final static String FILM_PARAM = "film";
    private final static String FILM_RATING = "rating";
    private final static String USER_RATING = "userRating";
    private static final String PARAM_LOCALE = "locale";
    private static final String PARAM_LANGUAGE = "language";

    private FilmService filmService;

    public FilmCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Film film = null;
        Double rating = null;
        User user = null;
        String filmId = request.getParameter("filmId");
        if(session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
        }
        Rating userRating = null;
        try {
            film = filmService.findEntityById(Integer.parseInt(filmId));
            rating = filmService.findFilmRating(Integer.parseInt(filmId));
            if (user != null) {
                userRating = filmService.findUserMarkToFilm(user.getUserId(), Integer.parseInt(filmId));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }


        String currLocale = (String) session.getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);

        request.setAttribute(USER_RATING, userRating);
        request.setAttribute(FILM_PARAM, film);
        request.setAttribute(FILM_RATING, rating);

        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_FILM_PAGE);
    }

}
