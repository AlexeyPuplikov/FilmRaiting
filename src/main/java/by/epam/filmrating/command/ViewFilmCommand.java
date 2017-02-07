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
    private final static String FILM_PARAM = "film";
    private final static String FILM_RATING = "rating";
    private final static String USER_MARK = "userMark";
    private final static String COMMENT_USERS_PARAM = "commentUsers";
    private final static String COMMENTS = "comments";
    private static final String PARAM_LOCALE = "locale";
    private static final String PARAM_LANGUAGE = "language";

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
        HttpSession session = request.getSession(true);
        Film film = null;
        Double filmRating = null;
        User user = null;
        List<User> commentUsers = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        String filmId = request.getParameter("filmId");
        if(session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
        }
        Rating userMark = null;
        try {
            film = filmService.findEntityBySign(Integer.parseInt(filmId));
            filmRating = filmService.findFilmRating(Integer.parseInt(filmId));
            if (user != null) {
                userMark = filmService.findUserMarkToFilm(user.getUserId(), Integer.parseInt(filmId));
            }
            comments = commentService.findEntitiesByFilm(film.getFilmId());
            if (comments != null) {
                for (Comment comment : comments) {
                    commentUsers.add(userService.findEntityBySign(comment.getUserId()));
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }


        String currLocale = (String) session.getAttribute(PARAM_LANGUAGE);
        request.setAttribute(PARAM_LOCALE, currLocale);

        request.setAttribute(COMMENTS, comments);
        request.setAttribute(COMMENT_USERS_PARAM, commentUsers);
        request.setAttribute(USER_MARK, userMark);
        request.setAttribute(FILM_PARAM, film);
        request.setAttribute(FILM_RATING, filmRating);

        ConfigurationManager configurationManager = new ConfigurationManager();
        return configurationManager.getProperty(PATH_FILM_PAGE);
    }

}
