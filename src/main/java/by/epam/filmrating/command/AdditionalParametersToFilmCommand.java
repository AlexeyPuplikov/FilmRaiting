package by.epam.filmrating.command;

import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AdditionalParametersToFilmCommand implements ActionCommand {
    private final static String PATH_ADD_FILM_PAGE = "path.page.addFilm";

    private FilmService filmService;
    private ActorService actorService;
    private GenreService genreService;
    private CountryService countryService;
    private StageDirectorService stageDirectorService;

    public AdditionalParametersToFilmCommand() {
        this.filmService = new FilmService();
        this.actorService = new ActorService();
        this.genreService = new GenreService();
        this.countryService = new CountryService();
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String film = request.getParameter("film");
        List<Actor> actors = null;
        List<Genre> genres = null;
        List<Country> countries = null;
        List<StageDirector> stageDirectors = null;
        List<Film> films = new ArrayList<>();
        try {
            films = filmService.findAll();
            stageDirectors = stageDirectorService.findAll();
            actors = actorService.findEntitiesNotInFilm(filmService.findEntityBySign(film).getFilmId());
            genres = genreService.findEntitiesNotInFilm(filmService.findEntityBySign(film).getFilmId());
            countries = countryService.findEntitiesNotInFilm(filmService.findEntityBySign(film).getFilmId());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("currentFilm", filmService.findEntityBySign(film));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute("allStageDirectors", stageDirectors);
        request.setAttribute("allFilms", films);
        request.setAttribute("allActors", actors);
        request.setAttribute("allGenres", genres);
        request.setAttribute("allCountries", countries);
        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_ADD_FILM_PAGE);
    }
}
