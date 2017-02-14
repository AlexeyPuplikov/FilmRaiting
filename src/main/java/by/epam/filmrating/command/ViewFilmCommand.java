package by.epam.filmrating.command;

import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.CommentService;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ViewFilmCommand implements ActionCommand {
    private final static String PATH_FILM_PAGE = "path.page.film";
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_FILM = "film";
    private final static String PARAM_USER = "user";
    private final static String PARAM_FILM_ID = "filmId";
    private final static String PARAM_FILM_RATING = "rating";
    private final static String PARAM_USER_MARK = "userMark";
    private final static String PARAM_USERS_COMMENT = "commentUsers";
    private final static String PARAM_COMMENTS = "comments";
    private static final String PARAM_LOCALE = "locale";
    private static final String PARAM_LANGUAGE = "language";
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
        ConfigurationManager configurationManager = new ConfigurationManager();
        HttpSession session = request.getSession(true);
        User user = null;
        int filmId = Integer.parseInt(request.getParameter(PARAM_FILM_ID));
        if(session.getAttribute(PARAM_USER) != null) {
            user = (User) session.getAttribute(PARAM_USER);
        }
        Rating userMark = null;
        try {
            Film film = filmService.findEntityBySign(filmId);
            Double filmRating = filmService.findFilmRating(filmId);
            if (user != null) {
                userMark = filmService.findUserMarkToFilm(user.getUserId(), filmId);
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
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        }

        String currLocale = (String) session.getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);
        request.setAttribute(PARAM_USER_MARK, userMark);
        return configurationManager.getProperty(PATH_FILM_PAGE);
    }

}
