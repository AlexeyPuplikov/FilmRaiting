package by.epam.filmrating.service;

import by.epam.filmrating.dao.GenreDAO;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class GenreService extends AbstractService<Genre> {
    private GenreDAO genreDAO;

    public GenreService() {
        this.genreDAO = new GenreDAO();
    }

    @Override
    public List<Genre> findAll() throws ServiceException {
        List<Genre> genres;
        try {
            genres = genreDAO.findAll();
            LOG.info("Retrieving genre list: " + genres.size());

        } catch (DAOException ex) {
            LOG.error("Error while retrieving genres list. ", ex);
            throw new ServiceException(ex);
        }
        return genres;
    }

    @Override
    public Genre findEntityBySign(int id) throws ServiceException {
        Genre genre;
        try {
            genre = genreDAO.findEntityBySign(id);
            LOG.info("Retrieving genre by id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving genre by id. ", ex);
            throw new ServiceException(ex);
        }
        return genre;
    }

    @Override
    public Genre findEntityBySign(String name) throws ServiceException {
        Genre genre;
        try {
            genre = genreDAO.findEntityBySign(name);
            LOG.info("Retrieving genre by name: " + name);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving genre by name. ", ex);
            throw new ServiceException(ex);
        }
        return genre;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            LOG.info("Deleting genre by id: " + id);
            return genreDAO.delete(id);

        } catch (DAOException ex) {
            LOG.error("Error while deleting genre by id. ", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean create(Genre entity) throws ServiceException {
        try {
            LOG.info("creating genre");
            return genreDAO.create(entity);

        } catch (DAOException ex) {
            LOG.error("Error while creating genre. ", ex);
            throw new ServiceException("", ex);
        }
    }

    @Override
    public List<Genre> findEntitiesByFilm(int id) throws ServiceException {
        List<Genre> genres;
        try {
            genres = genreDAO.findEntitiesByFilm(id);
            LOG.info("Retrieving genres by film id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving genres by film. ", ex);
            throw new ServiceException(ex);
        }
        return genres;
    }

    public List<Genre> findEntitiesNotInFilm(int id) throws ServiceException {
        List<Genre> genres;
        try {
            genres = genreDAO.findEntitiesNotInFilm(id);
            LOG.info("Retrieving genres by film id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving genres by film. ", ex);
            throw new ServiceException(ex);
        }
        return genres;
    }
}
