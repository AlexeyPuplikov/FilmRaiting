import by.epam.filmrating.dao.*;
import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;

public class Test {
    public static void main(String[] args) {
        FilmDAO filmDAO = new FilmDAO();
        ActorDAO actorDAO = new ActorDAO();
        StageDirectorDAO stageDirectorDAO = new StageDirectorDAO();
        GenreDAO genreDAO = new GenreDAO();
        Rating rating = new Rating();
        rating.setUserId(4);
        rating.setFilmId(3);
        rating.setMark(8);
        try {
            Film film;
            //System.out.println(filmDAO.findAll());
            //System.out.println(filmDAO.findFilmRating(2));
            //filmDAO.setFilmRating(rating);
           // System.out.println(filmDAO.findUserMarkToFilm(1, 1));
            System.out.println(actorDAO.findEntityById(2));
            //System.out.println(filmDAO.findEntityById(3));
            //film = filmDAO.findEntityById(3);
            //film.setFilmId(10);
            //filmDAO.create(film);
            //filmDAO.addActorToFilm(film, actorDAO.findEntityById(5));
            //filmDAO.addStageDirectorToFilm(film, stageDirectorDAO.findEntityById(2));
            //filmDAO.addGenreToFilm(film, genreDAO.findEntityById(1));
            //System.out.println(actorDAO.findFilmEntity(10));
            //System.out.println(filmDAO.findFilmByActor(2));
            //dao.delete(1);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
