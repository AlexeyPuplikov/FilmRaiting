package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Entity;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {
    protected DBConnectionPool connectionPool;

    public abstract List<T> findAll() throws DAOException;
    public abstract T findEntityById(int id) throws DAOException;
    public abstract boolean delete(int id) throws DAOException;
    public abstract boolean create(T entity) throws DAOException;
    public abstract List<T> findFilmEntity(int id) throws DAOException;

    public void closeConnection(Connection connection) throws DAOException {
        connectionPool.freeConnection(connection);
    }

    public boolean deleteHandler(int id, String sql) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(sql, connection)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
    }
}
