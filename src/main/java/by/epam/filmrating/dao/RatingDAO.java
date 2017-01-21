package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO extends AbstractDAO<Rating> {
    private final static String SELECT_RATING = "SELECT * FROM rating";
    private final static String SELECT_RATING_BY_ID = "SELECT * FROM rating WHERE RATING_ID = ?";
    private final static String DELETE_RATING = "DELETE FROM rating WHERE RATING_ID = ?";
    private final static String INSERT_RATING = "INSERT INTO rating(RATING_ID, USER_ID, FILM_ID, MARK) VALUES(?,?,?,?)";

    public RatingDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Rating> findAll() throws ConnectionPoolException {
        List<Rating> ratings = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_RATING, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Rating rating = new Rating(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4));
                ratings.add(rating);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return ratings;
    }

    @Override
    public Rating findEntityById(int id) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        Rating rating = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_RATING_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            rating = new Rating(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4));
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return rating;
    }

    @Override
    public boolean delete(int id) throws ConnectionPoolException {
        return super.deleteHandler(id, DELETE_RATING);
    }

    @Override
    public boolean create(Rating rating) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_RATING, connection)) {
            preparedStatement.setInt(1, rating.getRatingId());
            preparedStatement.setInt(2, rating.getUserId());
            preparedStatement.setInt(3, rating.getFilmId());
            preparedStatement.setInt(4, rating.getMark());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        }
    }

    @Override
    public Rating update(Rating entity) {
        return null;
    }

    @Override
    public List<Rating> findFilmEntity(int filmId) throws ConnectionPoolException {
        return null;
    }
}
