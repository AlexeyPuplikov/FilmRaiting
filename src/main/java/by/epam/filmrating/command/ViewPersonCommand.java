package by.epam.filmrating.command;

import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.ActorService;
import by.epam.filmrating.service.StageDirectorService;

import javax.servlet.http.HttpServletRequest;

public class ViewPersonCommand implements ActionCommand {
    private final static String PATH_PERSON_PAGE = "path.page.person";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private static final String PARAM_LOCALE = "locale";
    private static final String PARAM_LANGUAGE = "language";
    private final static String PARAM_PERSON_NAME = "personName";
    private final static String PARAM_PERSON = "person";

    private ActorService actorService;
    private StageDirectorService stageDirectorService;

    public ViewPersonCommand() {
        this.actorService = new ActorService();
        this.stageDirectorService = new StageDirectorService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String personName = request.getParameter(PARAM_PERSON_NAME);
        try {
            if (actorService.findEntityBySign(personName) != null) {
                Actor actor = actorService.findEntityBySign(personName);
                request.setAttribute(PARAM_PERSON, actor);
            } else {
                StageDirector stageDirector = stageDirectorService.findEntityBySign(personName);
                request.setAttribute(PARAM_PERSON, stageDirector);
            }
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        }
        String currLocale = (String) request.getSession().getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);
        return configurationManager.getProperty(PATH_PERSON_PAGE);
    }
}
