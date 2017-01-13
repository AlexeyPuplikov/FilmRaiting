package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<K, T extends Entity>{
    protected DBConnectionPool connectionPool;

    public abstract List<T> findAll();
    public abstract T findEntityById(K id);
    public abstract boolean delete(T entity);
    public abstract boolean delete(K id);
    public abstract boolean create(T entity);
    public abstract T update(T entity);

    public void closeConnection() {
        connectionPool.freeConnection();
    }

    protected void closeStatement(PreparedStatement preparedStatement) {
        connectionPool.closePrepareStatement(preparedStatement);
    }
}
