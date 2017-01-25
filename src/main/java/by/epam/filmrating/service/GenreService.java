package by.epam.filmrating.service;

import by.epam.filmrating.dao.GenreDAO;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class GenreService extends AbstractService<Genre> {
    private GenreDAO genreDAO;

    @Override
    public List<Genre> findAll() throws ServiceException {
        List<Genre> genres;
        try {
            genres = genreDAO.findAll();
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return genres;
    }

    @Override
    public Genre findEntityById(int id) throws ServiceException {
        Genre genre;
        try {
            genre = genreDAO.findEntityById(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return genre;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return genreDAO.delete(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public boolean create(Genre entity) throws ServiceException {
        try {
            return genreDAO.create(entity);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public List<Genre> findFilmEntity(int id) throws ServiceException {
        List<Genre> genres;
        try {
            genres = genreDAO.findFilmEntity(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return genres;
    }
}
