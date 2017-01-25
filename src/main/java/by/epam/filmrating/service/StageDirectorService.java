package by.epam.filmrating.service;

import by.epam.filmrating.dao.StageDirectorDAO;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class StageDirectorService extends AbstractService<StageDirector> {
    private StageDirectorDAO stageDirectorDAO;

    @Override
    public List<StageDirector> findAll() throws ServiceException {
        List<StageDirector> stageDirectors;
        try {
            stageDirectors = stageDirectorDAO.findAll();
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return stageDirectors;
    }

    @Override
    public StageDirector findEntityById(int id) throws ServiceException {
        StageDirector stageDirector;
        try {
            stageDirector = stageDirectorDAO.findEntityById(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return stageDirector;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return stageDirectorDAO.delete(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public boolean create(StageDirector entity) throws ServiceException {
        try {
            return stageDirectorDAO.create(entity);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
    }

    @Override
    public List<StageDirector> findFilmEntity(int id) throws ServiceException {
        List<StageDirector> stageDirectors;
        try {
            stageDirectors = stageDirectorDAO.findFilmEntity(id);
        } catch (DAOException | ConnectionPoolException ex) {
            throw new ServiceException("", ex);
        }
        return stageDirectors;
    }
}
