package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.StageDirectorService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFilmCommand implements ActionCommand {
    private final static String PATH_MAIN_ADMIN_PAGE = "path.page.admin";

    private StageDirectorService stageDirectorService;

    private FilmService filmService;


    public AddFilmCommand() {
        this.stageDirectorService = new StageDirectorService();
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String name = request.getParameter("name");
        String year = request.getParameter("year");
        String description = request.getParameter("description");
        String premiere = request.getParameter("premiere");
        String time = request.getParameter("time");
        String budget = request.getParameter("budget");
        String stageDirectorName = request.getParameter("stageDirector");
        Film film = new Film();
        film.setName(name);
        film.setYear(Integer.parseInt(year));
        film.setDescription(description);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date premiereDate = new Date();
        try {
            premiereDate = format.parse(premiere);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        film.setPremiere(premiereDate);
        film.setTime(Integer.parseInt(time));
        film.setBudget(Integer.parseInt(budget));
        StageDirector stageDirector = new StageDirector();
        try {
            stageDirector = stageDirectorService.findEntityByName(stageDirectorName);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        film.setStageDirector(stageDirector);
        try {
            filmService.create(film);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return configurationManager.getProperty(PATH_MAIN_ADMIN_PAGE);
    }
}
