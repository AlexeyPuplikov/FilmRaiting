package by.epam.filmrating.command;

import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.GenreService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddGenreCommand implements ActionCommand {
    private GenreService genreService;

    public AddGenreCommand() {
        this.genreService = new GenreService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("genreName");
        HttpSession httpSession = request.getSession();
        if(checkName(name)) {
            Genre genre = new Genre();
            genre.setName(name);
            try {
                genreService.create(genre);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            httpSession.removeAttribute("genreAddError");
            return "redirect:/controller?command=ADD_PARAMETERS_TO_FILM";
        } else {
            httpSession.setAttribute("actorAddError", "Такой жанр уже добавлен");
            return "redirect:/controller?command=ADD_PARAMETERS_TO_FILM";
        }
    }

    private boolean checkName(String name) {
        List<Genre> genres;
        boolean check = true;
        try {
            genres = this.genreService.findAll();
            for (Genre genre : genres) {
                if (genre.getName().equals(name)) {
                    check = false;
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return check;
    }
}
