package by.epam.filmrating.service;

import by.epam.filmrating.dao.FilmDAO;
import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class FilmService extends AbstractService<Film> {
    private FilmDAO filmDAO;

    public FilmService() {
        filmDAO = new FilmDAO();
    }

    @Override
    public List<Film> findAll() throws ServiceException {
        List<Film> films;
        try {
            films = filmDAO.findAll();
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return films;
    }

    @Override
    public Film findEntityById(int id) throws ServiceException {
        Film film;
        try {
            film = filmDAO.findEntityById(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return film;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return filmDAO.delete(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public boolean create(Film entity) throws ServiceException {
        try {
            return filmDAO.create(entity);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public List<Film> findFilmEntity(int id) throws ServiceException {
        return null;
    }

    public boolean addStageDirectorToFilm(Film film, StageDirector stageDirector) throws ServiceException {
        try {
            return filmDAO.addStageDirectorToFilm(film, stageDirector);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    public boolean addActorToFilm(Film film, Actor actor) throws ServiceException {
        try {
            return filmDAO.addActorToFilm(film, actor);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    public boolean addGenreToFilm(Film film, Genre genre) throws ServiceException {
        try {
            return filmDAO.addGenreToFilm(film, genre);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    public List<Film> findFilmByActor(int actorId) throws ServiceException {
        try {
            return filmDAO.findFilmByActor(actorId);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    public List<Film> findFilmByStageDirector(int stageDirectorId) throws ServiceException {
        try {
            return filmDAO.findFilmByStageDirector(stageDirectorId);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    public List<Film> findFilmByGenre(int genreId) throws ServiceException {
        try {
            return filmDAO.findFilmByGenre(genreId);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    public double findFilmRating(int filmId) throws ServiceException {
        try {
            return filmDAO.findFilmRating(filmId);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    public boolean setFilmRating(Rating rating) throws ServiceException {
        try {
            return filmDAO.setFilmRating(rating);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    public Rating findUserMarkToFilm(int userId, int filmId) throws ServiceException {
        try {
            return filmDAO.findUserMarkToFilm(userId, filmId);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }
}
