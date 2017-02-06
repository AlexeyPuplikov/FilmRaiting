package by.epam.filmrating.command;

import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;

import javax.servlet.http.HttpServletRequest;

public class SetRatingCommand implements ActionCommand {
    private FilmService filmService;

    public SetRatingCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String user = request.getParameter("userId");
        String film = request.getParameter("filmId");
        String mark = request.getParameter("mark");
        Rating rating = new Rating();
        rating.setUserId(Integer.parseInt(user));
        rating.setFilmId(Integer.parseInt(film));
        rating.setMark(Integer.parseInt(mark));
        try {
            filmService.setFilmRating(rating);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "redirect:/controller?command=VIEW_FILM&filmId=" + film;
    }
}
