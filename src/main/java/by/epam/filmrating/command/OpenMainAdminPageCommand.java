package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenMainAdminPageCommand implements ActionCommand {
    private final static String PARAM_FILMS = "films";
    private final static String PATH_ADMIN_PAGE = "path.page.admin";

    private FilmService filmService;

    public OpenMainAdminPageCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Film> films = null;
        try {
            films = filmService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute(PARAM_FILMS, films);
        ConfigurationManager configurationManager = new ConfigurationManager();
        if (request.getSession().getAttribute("admin") != null) {
            return configurationManager.getProperty(PATH_ADMIN_PAGE);
        }
        return configurationManager.getProperty("path.page.login");
    }
}
