package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {
    private final static String SELECT_USER = "SELECT u.USER_ID, u.LOGIN, u.PASSWORD, s.NAME AS STATUS, r.NAME AS ROLE FROM USER u INNER JOIN RATING.STATUS s ON s.STATUS_ID = u.STATUS_ID INNER JOIN RATING.ROLE r ON r.ROLE_ID = u.ROLE_ID GROUP BY u.USER_ID";
    private final static String SELECT_USER_BY_ID = "SELECT u.USER_ID, u.LOGIN, u.PASSWORD, s.NAME AS STATUS, r.NAME AS ROLE FROM USER u INNER JOIN RATING.STATUS s ON s.STATUS_ID = u.STATUS_ID INNER JOIN RATING.ROLE r ON r.ROLE_ID = u.ROLE_ID WHERE u.USER_ID = ?";
    private final static String INSERT_USER = "INSERT INTO USER(USER_ID, LOGIN, PASSWORD, STATUS_ID, ROLE_ID) VALUES(?,?,?,?,?)";
    private final static String UPDATE_STATUS = "UPDATE USER SET STATUS_ID = ? WHERE USER_ID = ?";

    private final static String USER_ID = "USER_ID";
    private final static String LOGIN = "LOGIN";
    private final static String PASSWORD = "PASSWORD";
    private final static String STATUS = "STATUS";
    private final static String ROLE = "ROLE";

    public UserDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getInt(USER_ID), resultSet.getString(LOGIN), resultSet.getString(PASSWORD), resultSet.getString(STATUS), EnumRole.valueOf(resultSet.getString(ROLE)).name());
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return users;
    }

    @Override
    public User findEntityById(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        User user = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getInt(USER_ID), resultSet.getString(LOGIN), resultSet.getString(PASSWORD), resultSet.getString(STATUS), EnumRole.valueOf(resultSet.getString(ROLE)).name());
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public boolean create(User user) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_USER, connection)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, EnumStatus.valueOf(String.valueOf(user.getStatus())).ordinal());
            preparedStatement.setInt(5, EnumRole.valueOf(String.valueOf(user.getRole())).ordinal());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return false;
    }

    @Override
    public List<User> findFilmEntity(int filmId) throws DAOException {
        return null;
    }

    public boolean updateStatus(int userId, String status) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(UPDATE_STATUS, connection)) {
            preparedStatement.setInt(1, EnumStatus.valueOf(status).ordinal() + 1);
            preparedStatement.setInt(2, userId);
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }finally {
            this.closeConnection(connection);
        }
    }
}
