package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.StageDirectorService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFilmCommand implements ActionCommand {
    private final static String SUCCESSFUL_ADD_FILM_PARAM = "successfulAddFilm";
    private final static String SUCCESSFUL_ADD_FILM_TEXT = "Фильм успешно добавлен";

    private StageDirectorService stageDirectorService;
    private FilmService filmService;

    public AddFilmCommand() {
        this.stageDirectorService = new StageDirectorService();
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
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
        Date premiereDate;
        try {
            premiereDate = format.parse(premiere);
            film.setPremiere(premiereDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        film.setTime(Integer.parseInt(time));
        film.setBudget(Integer.parseInt(budget));
        StageDirector stageDirector;
        try {
            stageDirector = stageDirectorService.findEntityBySign(stageDirectorName);
            film.setStageDirector(stageDirector);
            filmService.create(film);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "redirect:/controller?command=OPEN_ADD_FILM_PAGE";
    }
}
