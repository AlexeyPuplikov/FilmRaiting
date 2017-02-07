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
        String film = request.getParameter("filmId");
        String mark = request.getParameter("mark");
        Rating rating = new Rating();
        rating.setUserId(currentUser.getUserId());
        rating.setFilmId(Integer.parseInt(film));
        rating.setMark(Integer.parseInt(mark));
        List<User> users;
        List<Rating> userMark = new ArrayList<>();
        Double filmRating;
        Double difference;
        try {
            users = userService.findAll();
            filmService.setFilmRating(rating);
            for (User user : users) {
                userMark.add(filmService.findUserMarkToFilm(user.getUserId(), Integer.parseInt(film)));
            }
            filmRating = filmService.findFilmRating(Integer.parseInt(film));
            difference = Math.abs(filmRating - Integer.parseInt(mark));
            if (userMark.size() >= 5 && (difference <= 2 || difference >= 2) && EnumStatus.valueOf(currentUser.getStatus()).ordinal() < 5) {
                userService.updateStatus(currentUser.getUserId(), EnumStatus.valueOf(currentUser.getStatus()).ordinal() + 2);
                httpSession.removeAttribute("status");
                httpSession.setAttribute("status", EnumStatus.valueOf(userService.findEntityBySign(currentUser.getUserId()).getStatus()).getName());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "redirect:/controller?command=VIEW_FILM&filmId=" + film;
    }
}
