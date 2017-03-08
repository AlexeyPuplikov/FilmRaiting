package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {
    private final static String SELECT_USER = "SELECT U.USER_ID, U.LOGIN, U.PASSWORD, S.NAME AS STATUS, R.NAME AS ROLE, IS_BLOCKED FROM USER U INNER JOIN RATING.STATUS S ON S.STATUS_ID = U.STATUS_ID INNER JOIN RATING.ROLE R ON R.ROLE_ID = U.ROLE_ID GROUP BY U.USER_ID";
    private final static String SELECT_USER_BY_ID = "SELECT U.USER_ID, U.LOGIN, U.PASSWORD, S.NAME AS STATUS, R.NAME AS ROLE, IS_BLOCKED FROM USER U INNER JOIN RATING.STATUS S ON S.STATUS_ID = U.STATUS_ID INNER JOIN RATING.ROLE R ON R.ROLE_ID = U.ROLE_ID WHERE U.USER_ID = ?";
    private final static String SELECT_USER_BY_LOGIN = "SELECT U.USER_ID, U.LOGIN, U.PASSWORD, S.NAME AS STATUS, R.NAME AS ROLE, IS_BLOCKED FROM USER U INNER JOIN RATING.STATUS S ON S.STATUS_ID = U.STATUS_ID INNER JOIN RATING.ROLE R ON R.ROLE_ID = U.ROLE_ID WHERE U.LOGIN = ?";
    private final static String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT U.USER_ID, U.LOGIN, U.PASSWORD, S.NAME AS STATUS, R.NAME AS ROLE, IS_BLOCKED FROM USER U INNER JOIN RATING.STATUS S ON S.STATUS_ID = U.STATUS_ID INNER JOIN RATING.ROLE R ON R.ROLE_ID = U.ROLE_ID WHERE U.LOGIN = ? AND U.PASSWORD = ?";
    private final static String INSERT_USER = "INSERT INTO USER(LOGIN, PASSWORD, STATUS_ID, ROLE_ID) VALUES(?,?,?,?)";
    private final static String UPDATE_STATUS = "UPDATE USER SET STATUS_ID = ? WHERE USER_ID = ?";
    private final static String UPDATE_IS_BLOCKED = "UPDATE USER SET IS_BLOCKED = ? WHERE USER_ID = ?";

    private final static String USER_ID = "USER_ID";
    private final static String LOGIN = "LOGIN";
    private final static String PASSWORD = "PASSWORD";
    private final static String STATUS = "STATUS";
    private final static String ROLE = "ROLE";
    private final static String IS_BLOCKED = "IS_BLOCKED";

    public UserDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER, connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getInt(USER_ID), resultSet.getString(LOGIN), resultSet.getString(PASSWORD), resultSet.getString(STATUS), EnumRole.valueOf(resultSet.getString(ROLE)).name(), resultSet.getBoolean(IS_BLOCKED));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findAll user method.", ex);
        } finally {
            this.closeConnection(connection);
        }
        return users;
    }

    @Override
    public User findEntityBySign(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(resultSet.getInt(USER_ID), resultSet.getString(LOGIN), resultSet.getString(PASSWORD), resultSet.getString(STATUS), EnumRole.valueOf(resultSet.getString(ROLE)).name(), resultSet.getBoolean(IS_BLOCKED));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findUserById method.", ex);
        } finally {
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User findEntityBySign(String name) throws DAOException {
        throw new DAOException("This method is not implemented.");
    }

    @Override
    public boolean create(User user) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_USER, connection)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, EnumStatus.valueOf(user.getStatus()).ordinal() + 1);
            preparedStatement.setInt(4, EnumRole.valueOf(String.valueOf(user.getRole())).ordinal() + 1);
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing create user method.", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws DAOException {
        throw new DAOException("This method is not implemented.");
    }

    @Override
    public List<User> findEntitiesByFilm(int filmId) throws DAOException {
        throw new DAOException("This method is not implemented.");
    }

    public boolean updateStatus(int userId, int statusId) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(UPDATE_STATUS, connection)) {
            preparedStatement.setInt(1, statusId);
            preparedStatement.setInt(2, userId);
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing updateStatus method.", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    public boolean updateIsBlocked(int userId, boolean isBlocked) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(UPDATE_IS_BLOCKED, connection)) {
            preparedStatement.setBoolean(1, isBlocked);
            preparedStatement.setInt(2, userId);
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing updateIsBlocked method.", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    public User findUserByLogin(String login) throws DAOException {
        Connection connection = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER_BY_LOGIN, connection)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(resultSet.getInt(USER_ID), resultSet.getString(LOGIN), resultSet.getString(PASSWORD), resultSet.getString(STATUS), EnumRole.valueOf(resultSet.getString(ROLE)).name(), resultSet.getBoolean(IS_BLOCKED));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findUserByLogin method.", ex);
        } finally {
            this.closeConnection(connection);
        }
        return user;
    }

    public User findUserByLoginAndPassword(String login, String password) throws DAOException {
        Connection connection = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD, connection)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(resultSet.getInt(USER_ID), resultSet.getString(LOGIN), resultSet.getString(PASSWORD), resultSet.getString(STATUS), EnumRole.valueOf(resultSet.getString(ROLE)).name(), resultSet.getBoolean(IS_BLOCKED));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findUserByLoginAndPassword method.", ex);
        } finally {
            this.closeConnection(connection);
        }
        return user;
    }
}
