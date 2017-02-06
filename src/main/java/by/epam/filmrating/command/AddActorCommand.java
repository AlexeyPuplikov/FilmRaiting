package by.epam.filmrating.command;

import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.ActorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddActorCommand implements ActionCommand {
    private ActorService actorService;

    public AddActorCommand() {
        this.actorService = new ActorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String name = request.getParameter("actorName");
        String dateOfBirth = request.getParameter("actorDOB");
        String info = request.getParameter("infoActor");
        HttpSession httpSession = request.getSession();
        if(checkName(name)) {
            Actor actor = new Actor();
            actor.setName(name);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date DOBDate = new Date();
            try {
                DOBDate = format.parse(dateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            actor.setDateOfBirth(DOBDate);
            actor.setInfo(info);
            try {
                actorService.create(actor);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            httpSession.removeAttribute("actorAddError");
            return "redirect:/controller?command=ADD_PARAMETERS_TO_FILM";
        } else {
            httpSession.setAttribute("actorAddError", "Такой aктёр уже добавлен");
            return "redirect:/controller?command=ADD_PARAMETERS_TO_FILM";
        }
    }

    private boolean checkName(String name) {
        List<Actor> actors;
        boolean check = true;
        try {
            actors = this.actorService.findAll();
            for (Actor actor : actors) {
                if (actor.getName().equals(name)) {
                    check = false;
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return check;
    }
}
