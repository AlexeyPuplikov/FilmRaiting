package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.StageDirectorService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenAddFilmPageCommand implements ActionCommand {
    private final static String PATH_ADD_FILM_PAGE = "path.page.addFilm";

    private FilmService filmService;
    private StageDirectorService stageDirectorService;

    public OpenAddFilmPageCommand() {
        this.filmService = new FilmService();
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Film> films = null;
        List<StageDirector> stageDirectors = null;
        try {
            films = filmService.findAll();
            stageDirectors = stageDirectorService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute("allStageDirectors", stageDirectors);
        request.setAttribute("allFilms", films);
        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_ADD_FILM_PAGE);
    }
}
