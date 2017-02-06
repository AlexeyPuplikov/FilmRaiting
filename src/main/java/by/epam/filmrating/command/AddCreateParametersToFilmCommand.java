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

public class AddCreateParametersToFilmCommand implements ActionCommand {
    private final static String PATH_MAIN_ADMIN_PAGE = "path.page.admin";

    private FilmService filmService;
    private ActorService actorService;
    private GenreService genreService;
    private CountryService countryService;

    public AddCreateParametersToFilmCommand() {
        this.filmService = new FilmService();
        this.actorService = new ActorService();
        this.genreService = new GenreService();
        this.countryService = new CountryService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String film = request.getParameter("currentFilm");
        String[] actorNames = request.getParameterValues("actor");
        String[] genreNames = request.getParameterValues("genre");
        String[] countryNames = request.getParameterValues("country");
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        try {
            if (actorNames != null) {
                for (int i = 0; i < actorNames.length; i++) {
                    actors.add(actorService.findEntityByName(actorNames[i]));
                    filmService.addActorToFilm(filmService.findFilmByName(film), actors.get(i));
                }
            }
            if (genreNames != null) {
                for (int i = 0; i < genreNames.length; i++) {
                    genres.add(genreService.findEntityByName(genreNames[i]));
                    filmService.addGenreToFilm(filmService.findFilmByName(film), genres.get(i));
                }
            }
            if (countryNames != null) {
                for (int i = 0; i < countryNames.length; i++) {
                    countries.add(countryService.findEntityByName(countryNames[i]));
                    filmService.addCountryToFilm(filmService.findFilmByName(film), countries.get(i));
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return configurationManager.getProperty(PATH_MAIN_ADMIN_PAGE);
    }
}
