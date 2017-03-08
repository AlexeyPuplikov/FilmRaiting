package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.StageDirectorService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddStageDirectorCommandI implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_STAGE_DIRECTOR_NAME = "stageDirectorName";
    private final static String PARAM_DATE_OF_BIRTH = "stageDirectorDateOfBirth";
    private final static String PARAM_INFORMATION = "infoStageDirector";
    private final static String FORMAT_DATE = "yyyy-MM-dd";
    private final static String ADD_PARAMETERS_SUCCESSFUL = "successful";
    private final static String ADD_PARAMETERS_ERROR = "error";

    private StageDirectorService stageDirectorService;

    public AddStageDirectorCommandI() {
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String name = request.getParameter(PARAM_STAGE_DIRECTOR_NAME);
        String dateOfBirthParam = request.getParameter(PARAM_DATE_OF_BIRTH);
        String info = request.getParameter(PARAM_INFORMATION);
        if (checkName(name)) {
            StageDirector stageDirector = new StageDirector();
            stageDirector.setName(name);
            stageDirector.setInfo(info);
            try {
                Date dateOfBirth = new SimpleDateFormat(FORMAT_DATE).parse(dateOfBirthParam);
                stageDirector.setDateOfBirth(dateOfBirth);
            } catch (ParseException e) {
                request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
                return configurationManager.getProperty(PATH_ERROR_PAGE);
            }
            try {
                stageDirectorService.create(stageDirector);
            } catch (ServiceException e) {
                request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
                return configurationManager.getProperty(PATH_ERROR_PAGE);
            }
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE&successfulAddParameters=" + ADD_PARAMETERS_SUCCESSFUL;
        } else {
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE&errorAddParameters=" + ADD_PARAMETERS_ERROR;
        }
    }

    private boolean checkName(String name) {
        List<StageDirector> stageDirectors;
        boolean check = true;
        try {
            stageDirectors = this.stageDirectorService.findAll();
            for (StageDirector stageDirector : stageDirectors) {
                if (stageDirector.getName().equalsIgnoreCase(name)) {
                    check = false;
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return check;
    }
}
