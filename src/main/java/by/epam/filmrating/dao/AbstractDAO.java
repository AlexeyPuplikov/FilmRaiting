package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Entity;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {
    protected DBConnectionPool connectionPool;

    public abstract List<T> findAll() throws ConnectionPoolException;
    public abstract T findEntityById(int id) throws ConnectionPoolException;
    public abstract boolean delete(int id) throws ConnectionPoolException;
    public abstract boolean create(T entity) throws ConnectionPoolException;
    public abstract T update(T entity);
    public abstract List<T> findFilmEntity(int filmId) throws ConnectionPoolException;

    public void closeConnection(Connection connection) throws ConnectionPoolException {
        connectionPool.freeConnection(connection);
    }

    public void closeStatement(PreparedStatement preparedStatement) throws ConnectionPoolException {
        connectionPool.closePrepareStatement(preparedStatement);
    }

    public boolean deleteHandler(int id, String sql) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(sql, connection)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
    }
}
