package by.epam.filmrating.command;

import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.ActorService;
import by.epam.filmrating.service.CountryService;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.GenreService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SelectCreateParametersToFilmCommand implements ActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_CURRENT_FILM = "currentFilm";
    private final static String PARAM_ACTORS = "actor";
    private final static String PARAM_GENRES = "genre";
    private final static String PARAM_COUNTRIES = "country";
    private final static String ADD_PARAMETERS_SUCCESSFUL = "successful";

    private FilmService filmService;
    private ActorService actorService;
    private GenreService genreService;
    private CountryService countryService;

    public SelectCreateParametersToFilmCommand() {
        this.filmService = new FilmService();
        this.actorService = new ActorService();
        this.genreService = new GenreService();
        this.countryService = new CountryService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String film = request.getParameter(PARAM_CURRENT_FILM);
        String[] actorNames = request.getParameterValues(PARAM_ACTORS);
        String[] genreNames = request.getParameterValues(PARAM_GENRES);
        String[] countryNames = request.getParameterValues(PARAM_COUNTRIES);
        try {
            if (actorNames != null) {
                List<Actor> actors = new ArrayList<>();
                for (int i = 0; i < actorNames.length; i++) {
                    actors.add(actorService.findEntityBySign(actorNames[i]));
                    filmService.addActorToFilm(filmService.findEntityBySign(film), actors.get(i));
                }
            }
            if (genreNames != null) {
                List<Genre> genres = new ArrayList<>();
                for (int i = 0; i < genreNames.length; i++) {
                    genres.add(genreService.findEntityBySign(genreNames[i]));
                    filmService.addGenreToFilm(filmService.findEntityBySign(film), genres.get(i));
                }
            }
            if (countryNames != null) {
                List<Country> countries = new ArrayList<>();
                for (int i = 0; i < countryNames.length; i++) {
                    countries.add(countryService.findEntityBySign(countryNames[i]));
                    filmService.addCountryToFilm(filmService.findEntityBySign(film), countries.get(i));
                }
            }
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        }
        return "redirect:/controller?command=OPEN_ADD_FILM_PAGE&successfulAddParameters=" + ADD_PARAMETERS_SUCCESSFUL;
    }
}
