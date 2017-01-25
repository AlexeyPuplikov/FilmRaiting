package by.epam.filmrating.service;

import by.epam.filmrating.dao.CommentDAO;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class CommentService extends AbstractService<Comment> {
    private CommentDAO commentDAO;

    @Override
    public List<Comment> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Comment findEntityById(int id) throws ServiceException {
        return null;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return commentDAO.delete(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public boolean create(Comment entity) throws ServiceException {
        try {
            return commentDAO.create(entity);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public List<Comment> findFilmEntity(int id) throws ServiceException {
        List<Comment> comments;
        try {
            comments = commentDAO.findFilmEntity(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return comments;
    }
}
