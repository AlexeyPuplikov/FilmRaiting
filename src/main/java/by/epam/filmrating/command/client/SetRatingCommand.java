package by.epam.filmrating.command.client;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SetRatingCommand implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_USER = "user";
    private final static String PARAM_LOCALE = "locale";
    private final static String PARAM_STATUS = "status";
    private final static String PARAM_FILM_ID = "filmId";
    private final static String PARAM_MARK = "mark";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";

    private FilmService filmService;
    private UserService userService;

    public SetRatingCommand() {
        this.filmService = new FilmService();
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        User currentUser = (User) httpSession.getAttribute(PARAM_USER);
        int filmId = Integer.parseInt(request.getParameter(PARAM_FILM_ID));
        int mark = Integer.parseInt(request.getParameter(PARAM_MARK));
        Rating rating = new Rating();
        rating.setUserId(currentUser.getUserId());
        rating.setFilmId(filmId);
        rating.setMark(mark);
        try {
            filmService.setFilmRating(rating);
            this.updateStatus(filmId);
            httpSession.removeAttribute(PARAM_STATUS);
            httpSession.setAttribute(PARAM_STATUS, EnumStatus.valueOf(userService.findEntityBySign(currentUser.getUserId()).getStatus()).getName());
        } catch (ServiceException e) {
            request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) httpSession.getAttribute(PARAM_LOCALE)));
            return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
        }
        return "redirect:/controller?command=VIEW_FILM&filmId=" + filmId;
    }

    public void updateStatus(int filmId) throws ServiceException {
        List<User> users = userService.findAll();
        Double filmRating = filmService.findFilmRating(filmId);
        List<Rating> userMark = new ArrayList<>();
        for (User user : users) {
            if (filmService.findUserMarkToFilm(user.getUserId(), filmId) != null) {
                userMark.add(filmService.findUserMarkToFilm(user.getUserId(), filmId));
            }
        }
        for (Rating rating : userMark) {
            switch (EnumStatus.valueOf(userService.findEntityBySign(rating.getUserId()).getStatus()).ordinal()) {
                case 0:
                    if (userMark.size() > 4 && Math.abs(filmRating - rating.getMark()) <= 2) {
                        userService.updateStatus(rating.getUserId(), EnumStatus.valueOf(userService.findEntityBySign(rating.getUserId()).getStatus()).ordinal() + 2);
                    }
                    break;
                case 1:
                    if (userMark.size() > 8 && Math.abs(filmRating - rating.getMark()) <= 2) {
                        userService.updateStatus(rating.getUserId(), EnumStatus.valueOf(userService.findEntityBySign(rating.getUserId()).getStatus()).ordinal() + 2);
                    }
                    break;
                case 2:
                    if (userMark.size() > 12 && Math.abs(filmRating - rating.getMark()) <= 2) {
                        userService.updateStatus(rating.getUserId(), EnumStatus.valueOf(userService.findEntityBySign(rating.getUserId()).getStatus()).ordinal() + 2);
                    }
                    break;
            }
        }
    }
}

