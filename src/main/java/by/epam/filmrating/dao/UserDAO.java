package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.EnumRole;
import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {
    private final static String SELECT_USER = "select u.USER_ID, u.LOGIN, u.PASSWORD, s.NAME as status, r.name as role from user u\n" +
            "inner join rating.status s on s.STATUS_ID = u.STATUS_ID\n" +
            "inner join rating.role r on r.ROLE_ID = u.ROLE_ID\n" +
            "group by u.USER_ID";
    private final static String SELECT_USER_BY_ID = "select u.USER_ID, u.LOGIN, u.PASSWORD, s.NAME as status, r.name as role from user u\n" +
            "inner join rating.status s on s.STATUS_ID = u.STATUS_ID\n" +
            "inner join rating.role r on r.ROLE_ID = u.ROLE_ID\n" +
            "where u.USER_ID = ?";
    private final static String DELETE_USER = "DELETE FROM user WHERE USER_ID = ?";
    private final static String INSERT_USER = "INSERT INTO user(USER_ID, LOGIN, PASSWORD, STATUS_ID, ROLE_ID) VALUES(?,?,?,?,?)";

    public UserDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<User> findAll() throws ConnectionPoolException {
        List<User> users = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), EnumStatus.valueOf(resultSet.getString(4)), EnumRole.valueOf(resultSet.getString(5)));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return users;
    }

    @Override
    public User findEntityById(int id) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        User user = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), EnumStatus.valueOf(resultSet.getString(4)), EnumRole.valueOf(resultSet.getString(5)));
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public boolean delete(int id) throws ConnectionPoolException {
        return super.deleteHandler(id, DELETE_USER);
    }

    @Override
    public boolean create(User user) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_USER, connection)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, EnumStatus.valueOf(String.valueOf(user.getStatus())).ordinal());
            preparedStatement.setInt(5, EnumRole.valueOf(String.valueOf(user.getRole())).ordinal());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        }
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public List<User> findFilmEntity(int filmId) throws ConnectionPoolException {
        return null;
    }
}
