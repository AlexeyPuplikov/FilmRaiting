/*
package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Entity;
import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO extends AbstractDAO {

    private final static String SELECT_STATUS = "SELECT * FROM status";

    public StatusDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List findAll() throws ConnectionPoolException {
        List<EnumStatus> statuses = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS); ResultSet resultSet = preparedStatement.executeQuery()) {
            while(resultSet.next()) {
                EnumStatus status = EnumStatus.valueOf(resultSet.getString(1));
                statuses.add(status);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        }
        return statuses;
    }

    @Override
    public Entity findEntityById(Object id) {
        return null;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public boolean create(Entity entity) {
        return false;
    }

    @Override
    public Entity update(Entity entity) {
        return null;
    }
}
*/
