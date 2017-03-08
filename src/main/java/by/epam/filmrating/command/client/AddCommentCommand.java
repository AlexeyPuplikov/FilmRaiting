package by.epam.filmrating.command.client;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;

public class AddCommentCommand implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_USER = "user";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_FILM_ID = "filmId";
    private final static String PARAM_TEXT = "text";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";

    private CommentService commentService;

    public AddCommentCommand() {
        this.commentService = new CommentService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        int filmId = Integer.parseInt(request.getParameter(PARAM_FILM_ID));
        String text = request.getParameter(PARAM_TEXT);
        if (!text.isEmpty()) {
            Comment comment = new Comment();
            comment.setUserId(user.getUserId());
            comment.setFilmId(filmId);
            comment.setText(text);
            comment.setCreationDate(new Date());
            try {
                commentService.create(comment);
            } catch (ServiceException e) {
                request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
                return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
            }
        }
        return "redirect:/controller?command=VIEW_FILM&filmId=" + filmId;
    }
}
