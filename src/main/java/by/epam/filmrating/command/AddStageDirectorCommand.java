package by.epam.filmrating.command;

import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
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
        String name = request.getParameter("stageDirectorName");
        String dateOfBirthParam = request.getParameter("stageDirectorDateOfBirth");
        String info = request.getParameter("infoStageDirector");
        HttpSession httpSession = request.getSession();
        if (checkName(name)) {
            StageDirector stageDirector = new StageDirector();
            stageDirector.setName(name);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = null;
            try {
                dateOfBirth = format.parse(dateOfBirthParam);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            stageDirector.setDateOfBirth(dateOfBirth);
            stageDirector.setInfo(info);
            try {
                stageDirectorService.create(stageDirector);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            httpSession.removeAttribute("addError");
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE";
        } else {
            httpSession.setAttribute("addError", "Такой элемент уже добавлен");
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE";
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
