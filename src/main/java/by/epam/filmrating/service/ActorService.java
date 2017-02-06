package by.epam.filmrating.service;

import by.epam.filmrating.dao.ActorDAO;
import by.epam.filmrating.entity.Actor;
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
            LOG.info("Retrieving actors list with size: " + actors.size());

        } catch (DAOException ex) {
            LOG.error("Error while retrieving actors list. ", ex);
            throw new ServiceException(ex);
        }
        return actors;
    }

    @Override
    public Actor findEntityById(int id) throws ServiceException {
        Actor actor;
        try {
            actor = actorDAO.findEntityById(id);
            LOG.info("Retrieving actor by id: " + actor.getActorId());

        } catch (DAOException ex) {
            LOG.error("Error while retrieving actor by id.");
            throw new ServiceException(ex);
        }
        return actor;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            LOG.info("Deleting actor by id: " + id);
            return actorDAO.delete(id);

        } catch (DAOException ex) {
            LOG.error("Error while deleting actor by id.");
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean create(Actor entity) throws ServiceException {
        try {
            LOG.info("Creating actor");
            return actorDAO.create(entity);

        } catch (DAOException ex) {
            LOG.error("Error while creating actor.");
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Actor> findEntitiesByFilm(int id) throws ServiceException {
        List<Actor> actors;
        try {
            actors = actorDAO.findEntitiesByFilm(id);
            LOG.info("Retrieving actor by film id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving actor by film id.");
            throw new ServiceException(ex);
        }
        return actors;
    }

    public Actor findEntityByName(String name) throws ServiceException {
        Actor actor;
        try {
            actor = actorDAO.findEntityByName(name);
            LOG.info("Retrieving actor by name: " + actor.getName());

        } catch (DAOException ex) {
            LOG.error("Error while retrieving actor by name.");
            throw new ServiceException(ex);
        }
        return actor;
    }

    public List<Actor> findEntitiesNotInFilm(int id) throws ServiceException {
        List<Actor> actors;
        try {
            actors = actorDAO.findEntitiesNotInFilm(id);
            LOG.info("Retrieving actor by film id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving actor by film id.");
            throw new ServiceException(ex);
        }
        return actors;
    }
}
