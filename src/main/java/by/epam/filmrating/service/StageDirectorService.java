package by.epam.filmrating.service;

import by.epam.filmrating.dao.StageDirectorDAO;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class StageDirectorService extends AbstractService<StageDirector> {
    private StageDirectorDAO stageDirectorDAO;

    public StageDirectorService() {
        stageDirectorDAO = new StageDirectorDAO();
    }

    @Override
    public List<StageDirector> findAll() throws ServiceException {
        List<StageDirector> stageDirectors;
        try {
            stageDirectors = stageDirectorDAO.findAll();
            LOG.info("Retrieving stage directors list with size: " + stageDirectors.size());

        } catch (DAOException ex) {
            LOG.error("Error while retrieving stage directors.", ex);
            throw new ServiceException(ex);
        }
        return stageDirectors;
    }

    @Override
    public StageDirector findEntityBySign(int id) throws ServiceException {
        try {
            LOG.info("Retrieving stage director by id: " + id);
            return stageDirectorDAO.findEntityBySign(id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving stage director by id", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public StageDirector findEntityBySign(String name) throws ServiceException {
        try {
            LOG.info("Retrieving stage director by name: " + name);
            return stageDirectorDAO.findEntityBySign(name);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving stage director by name", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            LOG.info("Deleting stage director by id");
            return stageDirectorDAO.delete(id);

        } catch (DAOException ex) {
            LOG.error("Error while deleting stage director by id", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean create(StageDirector entity) throws ServiceException {
        try {
            LOG.info("Creating stage director");
            return stageDirectorDAO.create(entity);

        } catch (DAOException ex) {
            LOG.error("Error while creating stage director", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<StageDirector> findEntitiesByFilm(int id) throws ServiceException {
        return null;
    }

    public StageDirector findEntityByFilm(int id) throws ServiceException {
        StageDirector stageDirector;
        try {
            stageDirector = stageDirectorDAO.findEntityByFilm(id);
            LOG.info("Retrieving stage director by film: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving stage director", ex);
            throw new ServiceException(ex);
        }
        return stageDirector;
    }

}
