package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.EnumStatus;
import by.epam.filmrating.entity.Status;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO extends AbstractDAO<Status> {
    private final static String SELECT_STATUS = "SELECT * FROM status";
    private final static String SELECT_STATUS_BY_ID = "SELECT * FROM status WHERE STATUS_ID = ?";
    private final static String DELETE_STATUS = "DELETE FROM status WHERE STATUS_ID = ?";
    private final static String INSERT_STATUS = "INSERT INTO status(STATUS_ID, NAME) VALUES(?,?)";

    public StatusDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Status> findAll() throws ConnectionPoolException {
        List<Status> statuses = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STATUS, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Status status = new Status(resultSet.getInt(1), EnumStatus.valueOf(resultSet.getString(2)));
                statuses.add(status);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return statuses;
    }

    @Override
    public Status findEntityById(int id) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        Status status = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STATUS_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            status = new Status(resultSet.getInt(1), EnumStatus.valueOf(resultSet.getString(2)));
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return status;
    }

    @Override
    public boolean delete(int id) throws ConnectionPoolException {
        return super.deleteHandler(id, DELETE_STATUS);
    }

    @Override
    public boolean create(Status status) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_STATUS, connection)) {
            preparedStatement.setInt(1, status.getStatusId());
            preparedStatement.setString(2, String.valueOf(status.getName()));
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        }
    }

    @Override
    public Status update(Status entity) {
        return null;
    }

    @Override
    public List<Status> findFilmEntity(int filmId) throws ConnectionPoolException {
        return null;
    }
}
