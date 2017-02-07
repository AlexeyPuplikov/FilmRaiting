package by.epam.filmrating.service;

import by.epam.filmrating.entity.Entity;
import by.epam.filmrating.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractService<T extends Entity> {
    protected final static Logger LOG = LoggerFactory.getLogger(AbstractService.class);

    public abstract List<T> findAll() throws ServiceException;
    public abstract T findEntityBySign(int id) throws ServiceException;
    public abstract T findEntityBySign(String name) throws ServiceException;
    public abstract boolean delete(int id) throws ServiceException;
    public abstract boolean create(T entity) throws ServiceException;
    public abstract List<T> findEntitiesByFilm(int id) throws ServiceException;
}
