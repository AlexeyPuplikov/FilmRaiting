import by.epam.filmrating.dao.ActorDAO;
import by.epam.filmrating.dao.FilmDAO;
import by.epam.filmrating.dao.GenreDAO;
import by.epam.filmrating.dao.StageDirectorDAO;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;

public class Test {
    public static void main(String[] args) {
        FilmService filmService = new FilmService();
        try {
            System.out.println(filmService.findAll());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
