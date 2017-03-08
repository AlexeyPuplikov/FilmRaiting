package by.epam.filmrating.command.client;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.ActorService;
import by.epam.filmrating.service.StageDirectorService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class ViewPersonCommand implements IActionCommand {
    private final static String PATH_PERSON_PAGE = "path.page.person";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PATH_500_PAGE = "path.page.500Error";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private static final String PARAM_LOCALE = "locale";
    private final static String PARAM_STAGE_DIRECTOR_NAME = "stageDirectorName";
    private final static String PARAM_ACTOR_NAME = "actorName";
    private final static String PARAM_PERSON = "person";

    private ActorService actorService;
    private StageDirectorService stageDirectorService;

    public ViewPersonCommand() {
        this.actorService = new ActorService();
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String stageDirectorName = request.getParameter(PARAM_STAGE_DIRECTOR_NAME);
        String actorName = request.getParameter(PARAM_ACTOR_NAME);
        try {
            if (actorName != null) {
                Actor actor = actorService.findEntityBySign(actorName);
                if (actor != null) {
                    request.setAttribute(PARAM_PERSON, actor);
                } else {
                    return ConfigurationManager.getProperty(PATH_500_PAGE);
                }
            } else {
                if (stageDirectorName != null) {
                    StageDirector stageDirector = stageDirectorService.findEntityBySign(stageDirectorName);
                    if (stageDirector != null) {
                        request.setAttribute(PARAM_PERSON, stageDirector);
                    } else {
                        return ConfigurationManager.getProperty(PATH_500_PAGE);
                    }
                    request.setAttribute(PARAM_PERSON, stageDirector);
                }
            }
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
        return ConfigurationManager.getProperty(PATH_PERSON_PAGE);
    }
}
