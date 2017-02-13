package by.epam.filmrating.command;

import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class AddCommentCommand implements ActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_USER = "user";
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
        ConfigurationManager configurationManager = new ConfigurationManager();
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute(PARAM_USER);
        int filmId = Integer.parseInt(request.getParameter(PARAM_FILM_ID));
        String text = request.getParameter(PARAM_TEXT);
        Comment comment = new Comment();
        comment.setUserId(user.getUserId());
        comment.setFilmId(filmId);
        comment.setText(text);
        comment.setCreationDate(new Date());
        try {
            commentService.create(comment);
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            configurationManager.getProperty(PATH_ERROR_PAGE);
        }

        return "redirect:/controller?command=VIEW_FILM&filmId=" + filmId;
    }
}
