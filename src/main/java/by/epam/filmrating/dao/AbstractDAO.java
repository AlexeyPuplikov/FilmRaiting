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
    public abstract boolean delete(T entity);
    public abstract boolean delete(int id) throws ConnectionPoolException;
    public abstract boolean create(T entity) throws ConnectionPoolException;
    public abstract T update(T entity);

    public void closeConnection(Connection connection) throws ConnectionPoolException {
        connectionPool.freeConnection(connection);
    }

    void closeStatement(PreparedStatement preparedStatement) throws ConnectionPoolException {
        connectionPool.closePrepareStatement(preparedStatement);
    }
}
