package by.epam.filmrating.service;

import by.epam.filmrating.dao.UserDAO;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;

import java.util.List;

public class UserService extends AbstractService<User> {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> users;

        try {
            users = userDAO.findAll();
            LOG.info("Retrieving user list: " + users.size());

        } catch (DAOException ex) {
            LOG.error("Error while retrieving users list. ", ex);
            throw new ServiceException(ex);
        }
        return users;
    }

    @Override
    public User findEntityBySign(int id) throws ServiceException {
        User user;
        try {
            user = userDAO.findEntityBySign(id);
            LOG.info("Retrieving user by id: " + id);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving user by id. ", ex);
            throw new ServiceException(ex);
        }
        return user;
    }

    @Override
    public User findEntityBySign(String name) throws ServiceException {
        return null;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    @Override
    public boolean create(User entity) throws ServiceException {
        try {
            LOG.info("creating user");
            return userDAO.create(entity);

        } catch (DAOException ex) {
            LOG.error("Error while creating user. ", ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<User> findEntitiesByFilm(int id) throws ServiceException {
        return null;
    }

    public boolean updateStatus(int userId, int statusId) throws ServiceException {
        try {
            LOG.info("Update user status.");
            return userDAO.updateStatus(userId, statusId);

        } catch (DAOException ex) {
            LOG.error("Error while update user status.", ex);
            throw new ServiceException();
        }
    }

    public boolean updateIsBlocked(int userId, boolean isBlocked) throws ServiceException {
        try {
            LOG.info("Update user isBlocked.");
            return userDAO.updateIsBlocked(userId, isBlocked);

        } catch (DAOException ex) {
            LOG.error("Error while update user isBlocked.", ex);
            throw new ServiceException();
        }
    }

    public User findUserByLogin(String login, String password) throws ServiceException {
        User user;

        try {
            user = userDAO.findUserByLogin(login, password);
            LOG.info("Retrieving user by login: " + login);

        } catch (DAOException ex) {
            LOG.error("Error while retrieving user by login. ", ex);
            throw new ServiceException(ex);
        }
        return user;
    }
}
