package by.epam.filmrating.command;

import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.StageDirectorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddStageDirectorCommand implements ActionCommand {

    private StageDirectorService stageDirectorService;

    public AddStageDirectorCommand() {
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String name = request.getParameter("stageDirectorName");
        String dateOfBirth = request.getParameter("stageDirectorDOB");
        String info = request.getParameter("infoStageDirector");
        HttpSession httpSession = request.getSession();
        if(checkName(name)) {
            StageDirector stageDirector = new StageDirector();
            stageDirector.setName(name);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date DOBDate = new Date();
            try {
                DOBDate = format.parse(dateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            stageDirector.setDateOfBirth(DOBDate);
            stageDirector.setInfo(info);
            try {
                stageDirectorService.create(stageDirector);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            httpSession.removeAttribute("stageDirectorAddError");
            return "redirect:/controller?command=ADD_PARAMETERS_TO_FILM";
        } else {
            httpSession.setAttribute("stageDirectorAddError", "Такой режиссер уже добавлен");
            return "redirect:/controller?command=ADD_PARAMETERS_TO_FILM";
        }
    }

    private boolean checkName(String name) {
        List<StageDirector> stageDirectors;
        boolean check = true;
        try {
            stageDirectors = this.stageDirectorService.findAll();
            for (StageDirector stageDirector : stageDirectors) {
                if (stageDirector.getName().equals(name)) {
                    check = false;
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return check;
    }
}
