package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.StageDirectorService;
import by.epam.filmrating.util.AddUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFilmCommandI implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_NAME = "name";
    private final static String PARAM_YEAR = "year";
    private final static String PARAM_DESCRIPTION = "description";
    private final static String PARAM_PREMIERE = "premiere";
    private final static String PARAM_TIME = "time";
    private final static String PARAM_BUDGET = "budget";
    private final static String PARAM_STAGE_DIRECTOR = "stageDirector";
    private final static String FORMAT_DATE = "yyyy-MM-dd";
    private final static String ADD_FILM_ERROR = "error";
    private final static String ADD_FILM_SUCCESSFUL = "successful";

    private StageDirectorService stageDirectorService;
    private FilmService filmService;

    public AddFilmCommandI() {
        this.stageDirectorService = new StageDirectorService();
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        /*TextManager textManager = new TextManager(request.getLocale());*/
        AddUtil addUtil = new AddUtil();
        String name = request.getParameter(PARAM_NAME);
        int year = Integer.parseInt(request.getParameter(PARAM_YEAR));
        String description = request.getParameter(PARAM_DESCRIPTION);
        String premiere = request.getParameter(PARAM_PREMIERE);
        int time = Integer.parseInt(request.getParameter(PARAM_TIME));
        int budget = Integer.parseInt(request.getParameter(PARAM_BUDGET));
        String stageDirectorName = request.getParameter(PARAM_STAGE_DIRECTOR);
        if (addUtil.checkName(name) && addUtil.checkYear(year) && addUtil.checkDescription(description)
                && addUtil.checkBudget(budget) && addUtil.checkTime(time)) {
            Film film = new Film();
            film.setName(name);
            film.setYear(year);
            film.setDescription(description);
            film.setTime(time);
            film.setBudget(budget);
            try {
                Date premiereDate = new SimpleDateFormat(FORMAT_DATE).parse(premiere);
                film.setPremiere(premiereDate);
            } catch (ParseException e) {
                request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
                return configurationManager.getProperty(PATH_ERROR_PAGE);
            }
            try {
                StageDirector stageDirector = stageDirectorService.findEntityBySign(stageDirectorName);
                film.setStageDirector(stageDirector);
                filmService.create(film);
            } catch (ServiceException e) {
                request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
                return configurationManager.getProperty(PATH_ERROR_PAGE);
            }
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE&successfulAdd=" + ADD_FILM_SUCCESSFUL;
        } else {
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE&errorAdd=" + ADD_FILM_ERROR;
        }
    }
}
