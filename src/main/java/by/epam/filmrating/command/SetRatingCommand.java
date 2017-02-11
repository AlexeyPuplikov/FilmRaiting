package by.epam.filmrating.command;

import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class SetRatingCommand implements ActionCommand {
    private FilmService filmService;
    private UserService userService;

    public SetRatingCommand() {
        this.filmService = new FilmService();
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        User currentUser = (User) httpSession.getAttribute("user");
        int filmId = Integer.parseInt(request.getParameter("filmId"));
        int mark = Integer.parseInt(request.getParameter("mark"));
        Rating rating = new Rating();
        rating.setUserId(currentUser.getUserId());
        rating.setFilmId(filmId);
        rating.setMark(mark);
        try {
            filmService.setFilmRating(rating);
            if (this.updateStatus(filmId, mark, currentUser)) {
                httpSession.removeAttribute("user");
                httpSession.setAttribute("user", userService.findEntityBySign(currentUser.getUserId()));
                httpSession.removeAttribute("status");
                httpSession.setAttribute("status", EnumStatus.valueOf(userService.findEntityBySign(currentUser.getUserId()).getStatus()).getName());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "redirect:/controller?command=VIEW_FILM&filmId=" + filmId;
    }

    public boolean updateStatus(int filmId, int mark, User currentUser) throws ServiceException {
        List<User> users;
        List<Rating> userMark = new ArrayList<>();
        Double filmRating;
        Double difference;
        users = userService.findAll();
        for (User user : users) {
            if (filmService.findUserMarkToFilm(user.getUserId(), filmId) != null) {
                userMark.add(filmService.findUserMarkToFilm(user.getUserId(), filmId));
            }
        }
        filmRating = filmService.findFilmRating(filmId);
        difference = Math.abs(filmRating - mark);
        if (userMark.size() >= 5 && (difference <= 2 || difference >= 2) && EnumStatus.valueOf(currentUser.getStatus()).ordinal() != 3) {
            userService.updateStatus(currentUser.getUserId(), EnumStatus.valueOf(currentUser.getStatus()).ordinal() + 2);
            return true;
        } else {
            return false;
        }
    }
}
