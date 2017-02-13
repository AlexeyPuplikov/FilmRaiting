package by.epam.filmrating.command;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements ActionCommand {
    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_LOCALE = "locale";

    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter(PARAM_LANGUAGE);

        if (!language.isEmpty()) {
            request.getSession().setAttribute(PARAM_LANGUAGE, language);
            request.setAttribute(PARAM_LOCALE, language);
        }
        if (request.getSession().getAttribute("admin") != null) {
            return "/controller?command=OPEN_MAIN_ADMIN_PAGE";
        }
        return "/controller?command=VIEW_FILMS&page=1&recordsPerPage=4";
    }
}
