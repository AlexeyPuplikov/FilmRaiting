package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ViewFilmsCommand implements ActionCommand {
    private final static String PATH_FILM_PAGE = "path.page.result";
    private final static String FILMS_PARAM = "films";

    @Override
    public String execute(HttpServletRequest request) {
        List<Film> films = new ArrayList<>();
        FilmService filmService = new FilmService();
        try {
            films = filmService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        request.setAttribute(FILMS_PARAM, films);

        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_FILM_PAGE);
    }
}
