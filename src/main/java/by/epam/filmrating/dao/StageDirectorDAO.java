package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StageDirectorDAO extends AbstractDAO<StageDirector> {
    private final static String SELECT_STAGE_DIRECTOR = "SELECT * FROM stage_director";
    private final static String SELECT_STAGE_DIRECTOR_BY_ID = "SELECT * FROM stage_director WHERE STAGE_DIRECTOR_ID = ?";
    private final static String DELETE_STAGE_DIRECTOR = "DELETE FROM stage_director WHERE STAGE_DIRECTOR_ID = ?";
    private final static String INSERT_STAGE_DIRECTOR = "INSERT INTO stage_director(STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO) VALUES(?,?,?,?)";
    private final static String SELECT_FILM_STAGE_DIRECTOR = "SELECT * FROM stage_director sd\n" +
            "JOIN film_has_stage_director fhs\n" +
            "ON fhs.STAGE_DIRECTOR_ID = sd.STAGE_DIRECTOR_ID\n" +
            "WHERE fhs.FILM_ID = ?";

    public StageDirectorDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<StageDirector> findAll() throws ConnectionPoolException {
        List<StageDirector> stageDirectors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STAGE_DIRECTOR, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                StageDirector stageDirector = new StageDirector(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4));
                stageDirectors.add(stageDirector);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirectors;
    }

    @Override
    public StageDirector findEntityById(int id) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        StageDirector stageDirector = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STAGE_DIRECTOR_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            stageDirector = new StageDirector(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4));
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirector;
    }

    @Override
    public boolean delete(int id) throws ConnectionPoolException {
        return super.deleteHandler(id, DELETE_STAGE_DIRECTOR);
    }

    @Override
    public boolean create(StageDirector stageDirector) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_STAGE_DIRECTOR, connection)) {
            preparedStatement.setInt(1, stageDirector.getStageDirectorId());
            preparedStatement.setString(2, stageDirector.getName());
            preparedStatement.setDate(3, (Date) stageDirector.getDateOfBirth());
            preparedStatement.setString(4, stageDirector.getInfo());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        }
    }

    @Override
    public List<StageDirector> findFilmEntity(int filmId) throws ConnectionPoolException {
        List<StageDirector> stageDirectors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_STAGE_DIRECTOR, connection)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StageDirector stageDirector = new StageDirector(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4));
                stageDirectors.add(stageDirector);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirectors;
    }

    @Override
    public StageDirector update(StageDirector entity) {
        return null;
    }
}
