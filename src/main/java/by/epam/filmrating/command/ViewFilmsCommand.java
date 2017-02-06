package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewFilmsCommand implements ActionCommand {
    private final static String PATH_FILMS_PAGE = "path.page.films";
    private final static String FILMS_PARAM = "films";
    private static final String PARAM_LOCALE = "locale";
    private static final String PARAM_LANGUAGE = "language";

    private FilmService filmService;

    public ViewFilmsCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "C:\\Users\\user\\Desktop\\FinalProject\\FilmRaiting\\src\\main\\webapp\\resources\\images\\";
        String realPath;
        List<Film> films = new ArrayList<>();
        try {
            films = filmService.findAll();
            for (Film film : films) {
                realPath = path + film.getName() + ".jpg";
                if (!new File(realPath).exists()) {
                    filmService.loadCoverToFilm(film, realPath);
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        Collections.shuffle(films);

        request.setAttribute(FILMS_PARAM, films);
        HttpSession session = request.getSession(true);
        String currLocale = (String) session.getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);


        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_FILMS_PAGE);
    }
}
