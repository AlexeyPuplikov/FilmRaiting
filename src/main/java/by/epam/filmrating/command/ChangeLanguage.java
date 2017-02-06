package by.epam.filmrating.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguage implements ActionCommand {
    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_LOCALE = "locale";


    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter(PARAM_LANGUAGE);
        HttpSession session = request.getSession(true);

        if (!language.isEmpty()) {
            session.setAttribute(PARAM_LANGUAGE, language);
            request.setAttribute(PARAM_LOCALE, language);
        }

        if(request.getParameter("filmId") != null) {
            return "redirect:" + request.getParameter("page") + "&filmId=" + request.getParameter("filmId");
        } else {
            return "redirect:" + request.getParameter("page");
        }
    }
}
