package by.epam.filmrating.service;

import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.dao.ActorDAO;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class ActorService extends AbstractService<Actor> {
    private ActorDAO actorDAO;

    public ActorService() {
        actorDAO = new ActorDAO();
    }

    @Override
    public List<Actor> findAll() throws ServiceException {
        List<Actor> actors;
        try {
            actors = actorDAO.findAll();
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return actors;
    }

    @Override
    public Actor findEntityById(int id) throws ServiceException {
        Actor actor;
        try {
            actor = actorDAO.findEntityById(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return actor;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return actorDAO.delete(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public boolean create(Actor entity) throws ServiceException {
        try {
            return actorDAO.create(entity);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public List<Actor> findFilmEntity(int id) throws ServiceException {
        List<Actor> actors;
        try {
            actors = actorDAO.findFilmEntity(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return actors;
    }
}
