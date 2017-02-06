package by.epam.filmrating.command;

import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AddParametersToFilmCommand implements ActionCommand {
    private final static String PATH_ADD_FILM_PAGE = "path.page.addFilm";

    private FilmService filmService;
    private StageDirectorService stageDirectorService;

    public AddParametersToFilmCommand() {
        this.filmService = new FilmService();
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Film> films = new ArrayList<>();
        List<StageDirector> stageDirectors = new ArrayList<>();
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
