package by.epam.filmrating.command;

import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AddCorrectParametersToFilm implements ActionCommand {
    private final static String PATH_ADD_FILM_PAGE = "path.page.addFilm";

    private FilmService filmService;
    private ActorService actorService;
    private GenreService genreService;
    private CountryService countryService;
    private StageDirectorService stageDirectorService;

    public AddCorrectParametersToFilm() {
        this.filmService = new FilmService();
        this.actorService = new ActorService();
        this.genreService = new GenreService();
        this.countryService = new CountryService();
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String film = request.getParameter("film");
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        List<StageDirector> stageDirectors = new ArrayList<>();
        List<Film> films = new ArrayList<>();
        try {
            films = filmService.findAll();
            stageDirectors = stageDirectorService.findAll();
            actors = actorService.findEntitiesNotInFilm(filmService.findFilmByName(film).getFilmId());
            genres = genreService.findEntitiesNotInFilm(filmService.findFilmByName(film).getFilmId());
            countries = countryService.findEntitiesNotInFilm(filmService.findFilmByName(film).getFilmId());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("currentFilm", filmService.findFilmByName(film));
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
