package by.epam.filmrating.service;

import by.epam.filmrating.dao.CountryDAO;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class CountryService extends AbstractService<Country> {
    private CountryDAO countryDAO;

    public CountryService() {
        this.countryDAO = new CountryDAO();
    }

    @Override
    public List<Country> findAll() throws ServiceException {
        List<Country> countries;
        try {
            countries = countryDAO.findAll();
            LOG.info("Retrieving country list: " + countries.size());

        } catch (DAOException ex) {
            LOG.error("Error while retrieving genres list. ", ex);
            throw new ServiceException(ex);
        }
        return countries;
    }

    @Override
    public Country findEntityBySign(int id) throws ServiceException {
        Country country;
        try {
            country = countryDAO.findEntityBySign(id);
            LOG.info("Retrieving country by id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving country by id. ", ex);
            throw new ServiceException(ex);
        }
        return country;
    }

    @Override
    public Country findEntityBySign(String name) throws ServiceException {
        Country country;
        try {
            country = countryDAO.findEntityBySign(name);
            LOG.info("Retrieving country by name: " + name);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving country by name. ", name);
            throw new ServiceException(ex);
        }
        return country;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            LOG.info("Deleting country by id: " + id);
            return countryDAO.delete(id);

        } catch (DAOException ex) {
            LOG.error("Error while deleting country by id. ", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean create(Country entity) throws ServiceException {
        try {
            LOG.info("creating country");
            return countryDAO.create(entity);

        } catch (DAOException ex) {
            LOG.error("Error while creating country. ", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Country> findEntitiesByFilm(int id) throws ServiceException {
        List<Country> countries;
        try {
            countries = countryDAO.findEntitiesByFilm(id);
            LOG.info("Retrieving countries by film id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving countries by film. ", ex);
            throw new ServiceException(ex);
        }
        return countries;
    }

    public List<Country> findEntitiesNotInFilm(int id) throws ServiceException {
        List<Country> countries;
        try {
            countries = countryDAO.findEntitiesNotInFilm(id);
            LOG.info("Retrieving countries by film id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving countries by film. ", ex);
            throw new ServiceException(ex);
        }
        return countries;
    }
}
