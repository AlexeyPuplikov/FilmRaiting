package by.epam.filmrating.service;

import by.epam.filmrating.dao.FilmDAO;
import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.io.InputStream;
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
            LOG.info("Retrieving film list: " + films.size());
        } catch (DAOException ex) {
            LOG.error("Error while retrieving film list. ", ex);
            throw new ServiceException(ex);
        }
        return films;
    }

    @Override
    public Film findEntityById(int id) throws ServiceException {
        Film film;
        try {
            film = filmDAO.findEntityById(id);
            LOG.info("Retrieving film by id: " + film.getFilmId());
        } catch (DAOException ex) {
            LOG.error("Error while retrieving actor by id.");
            throw new ServiceException(ex);
        }
        return film;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            LOG.info("Deleting film by id: " + id);
            return filmDAO.delete(id);
        } catch (DAOException ex) {
            LOG.error("Error while deleting film by id.");
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean create(Film entity) throws ServiceException {
        try {
            LOG.info("Creating film.");
            return filmDAO.create(entity);
        } catch (DAOException ex) {
            LOG.error("Error while creating film.");
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Film> findEntitiesByFilm(int id) throws ServiceException {
        return null;
    }

    public boolean addActorToFilm(Film film, Actor actor) throws ServiceException {
        try {
            LOG.info("Adding actor by id: " + film.getFilmId());
            return filmDAO.addActorToFilm(film, actor);
        } catch (DAOException ex) {
            LOG.error("Error while adding actor to film.");
            throw new ServiceException(ex);
        }
    }

    public boolean addGenreToFilm(Film film, Genre genre) throws ServiceException {
        try {
            LOG.info("Adding genre by id: " + film.getFilmId());
            return filmDAO.addGenreToFilm(film, genre);
        } catch (DAOException ex) {
            LOG.error("Error while adding genre to film.");
            throw new ServiceException(ex);
        }
    }

    public boolean addCountryToFilm(Film film, Country country) throws ServiceException {
        try {
            LOG.info("Adding country to film: " + film.getFilmId());
            return filmDAO.addCountryToFilm(film, country);
        } catch (DAOException ex) {
            LOG.error("Error while adding country to film.");
            throw new ServiceException(ex);
        }
    }

    public boolean addCoverToFilm(InputStream inputStream, Film film) throws ServiceException {
        try {
            LOG.info("Adding cover to film: " + film.getFilmId());
            return filmDAO.addCoverToFilm(inputStream, film);
        } catch (DAOException ex) {
            LOG.error("Error while adding country to film.");
            throw new ServiceException(ex);
        }
    }

    public void loadCoverToFilm(Film film, String fileName) throws ServiceException {
        try {
            LOG.info("Adding cover to film: " + film.getFilmId());
            filmDAO.loadCoverToFilm(film, fileName);
        } catch (DAOException ex) {
            LOG.error("Error while adding country to film.");
            throw new ServiceException(ex);
        }
    }

    public List<Film> findFilmByActor(int actorId) throws ServiceException {
        try {
            LOG.info("Retrieving film by actor id: " + actorId);
            return filmDAO.findFilmByActor(actorId);
        } catch (DAOException ex) {
            LOG.error("Error while retrieving film by actor.");
            throw new ServiceException(ex);
        }
    }

    public List<Film> findFilmByStageDirector(int stageDirectorId) throws ServiceException {
        try {
            LOG.info("Retrieving film by stage director id: " + stageDirectorId);
            return filmDAO.findFilmByStageDirector(stageDirectorId);
        } catch (DAOException ex) {
            LOG.error("Error while retrieving film by stage director.");
            throw new ServiceException(ex);
        }
    }

    public List<Film> findFilmByGenre(int genreId) throws ServiceException {
        try {
            LOG.info("Retrieving film by genre id: " + genreId);
            return filmDAO.findFilmByGenre(genreId);
        } catch (DAOException ex) {
            LOG.error("Error while retrieving film by genre.");
            throw new ServiceException(ex);
        }
    }

    public List<Film> findFilmByCountry(int countryId) throws ServiceException {
        try {
            LOG.info("Retrieving film by country id: " + countryId);
            return filmDAO.findFilmByCountry(countryId);
        } catch (DAOException ex) {
            LOG.error("Error while retrieving film by country.");
            throw new ServiceException(ex);
        }
    }

    public double findFilmRating(int filmId) throws ServiceException {
        try {
            LOG.info("Retrieving rating by film id: " + filmId);
            return filmDAO.findFilmRating(filmId);
        } catch (DAOException ex) {
            LOG.error("Error while retrieving rating by film id.");
            throw new ServiceException(ex);
        }
    }

    public boolean setFilmRating(Rating rating) throws ServiceException {
        try {
            LOG.info("Setting rating to film: " + rating.getRatingId());
            return filmDAO.setFilmRating(rating);
        } catch (DAOException ex) {
            LOG.error("Error while setting rating to film.");
            throw new ServiceException(ex);
        }
    }

    public Rating findUserMarkToFilm(int userId, int filmId) throws ServiceException {
        try {
            LOG.info("Retrieving user mark to film: " + userId);
            return filmDAO.findUserMarkToFilm(userId, filmId);
        } catch (DAOException ex) {
            LOG.error("Error while retrieving user mark to film.");
            throw new ServiceException(ex);
        }
    }

    public Film findFilmByName(String name) throws ServiceException {
        Film film;
        try {
            film = filmDAO.findFilmByName(name);
            LOG.info("Retrieving film by name: " + film.getFilmId());
        } catch (DAOException ex) {
            LOG.error("Error while retrieving film by name.");
            throw new ServiceException(ex);
        }
        return film;
    }
}
