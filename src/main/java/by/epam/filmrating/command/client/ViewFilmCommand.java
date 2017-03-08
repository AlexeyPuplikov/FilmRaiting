package by.epam.filmrating.command.client;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.CommentService;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewFilmCommand implements IActionCommand {
    private final static String PATH_FILM_PAGE = "path.page.film";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PATH_500_PAGE = "path.page.500Error";
    private final static String PARAM_FILM = "film";
    private final static String PARAM_USER = "user";
    private final static String PARAM_FILM_ID = "filmId";
    private final static String PARAM_FILM_RATING = "rating";
    private final static String PARAM_USER_MARK = "userMark";
    private final static String PARAM_USERS_COMMENT = "commentUsers";
    private final static String PARAM_COMMENTS = "comments";
    private static final String PARAM_LOCALE = "locale";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";

    private FilmService filmService;
    private CommentService commentService;
    private UserService userService;

    public ViewFilmCommand() {
        this.filmService = new FilmService();
        this.commentService = new CommentService();
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = null;
        int filmId = Integer.parseInt(request.getParameter(PARAM_FILM_ID));
        if (request.getSession().getAttribute(PARAM_USER) != null) {
            user = (User) request.getSession().getAttribute(PARAM_USER);
        }
        try {
            Film film = filmService.findEntityBySign(filmId);
            if (film != null) {
                Double filmRating = filmService.findFilmRating(filmId);
                if (user != null) {
                    Rating userMark = filmService.findUserMarkToFilm(user.getUserId(), filmId);
                    request.setAttribute(PARAM_USER_MARK, userMark);
                }
                List<Comment> comments = commentService.findEntitiesByFilm(film.getFilmId());
                List<User> commentUsers = new ArrayList<>();
                if (comments != null) {
                    for (Comment comment : comments) {
                        commentUsers.add(userService.findEntityBySign(comment.getUserId()));
                    }
                }
                request.setAttribute(PARAM_USERS_COMMENT, commentUsers);
                request.setAttribute(PARAM_COMMENTS, comments);
                request.setAttribute(PARAM_FILM, film);
                request.setAttribute(PARAM_FILM_RATING, filmRating);
            } else {
                return ConfigurationManager.getProperty(PATH_500_PAGE);
            }
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
        return ConfigurationManager.getProperty(PATH_FILM_PAGE);
    }

}
