package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.ActorService;
import by.epam.filmrating.service.CountryService;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.GenreService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdditionalParametersToFilmCommandI implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_FILM = "film";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_ALL_ACTORS = "allActors";
    private final static String PARAM_ALL_GENRES = "allGenres";
    private final static String PARAM_ALL_COUNTRIES = "allCountries";
    private final static String PARAM_CURRENT_FILM = "currentFilm";

    private FilmService filmService;
    private ActorService actorService;
    private GenreService genreService;
    private CountryService countryService;

    public AdditionalParametersToFilmCommandI() {
        this.filmService = new FilmService();
        this.actorService = new ActorService();
        this.genreService = new GenreService();
        this.countryService = new CountryService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String filmName = request.getParameter(PARAM_FILM);
        try {
            Film film = filmService.findEntityBySign(filmName);
            List<Actor> actors = actorService.findEntitiesNotInFilm(film.getFilmId());
            List<Genre> genres = genreService.findEntitiesNotInFilm(film.getFilmId());
            List<Country> countries = countryService.findEntitiesNotInFilm(film.getFilmId());
            request.setAttribute(PARAM_ALL_ACTORS, actors);
            request.setAttribute(PARAM_ALL_GENRES, genres);
            request.setAttribute(PARAM_ALL_COUNTRIES, countries);
            request.setAttribute(PARAM_CURRENT_FILM, film);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        }
        return "/controller?command=OPEN_ADD_FILM_PAGE";
    }
}
