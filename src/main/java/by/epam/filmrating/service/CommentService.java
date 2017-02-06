package by.epam.filmrating.service;

import by.epam.filmrating.dao.CommentDAO;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class CommentService extends AbstractService<Comment> {
    private CommentDAO commentDAO;

    public CommentService() {
        commentDAO = new CommentDAO();
    }

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
            LOG.info("Deleting comment by id: " + id);
            return commentDAO.delete(id);

        } catch (DAOException ex) {
            LOG.error("Error while deleting comment by id. ", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean create(Comment entity) throws ServiceException {
        try {
            LOG.info("Creating comment");
            return commentDAO.create(entity);

        } catch (DAOException ex) {
            LOG.error("Error while creating comment", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Comment> findEntitiesByFilm(int id) throws ServiceException {
        try {
            LOG.info("Retrieving comment by film id: " + id);
            return commentDAO.findEntitiesByFilm(id);
        } catch (DAOException ex) {
            LOG.error("Error while retrieving comment by film id", ex);
            throw new ServiceException(ex);
        }
    }
}
