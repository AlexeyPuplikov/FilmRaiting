package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.DAOException;

import javax.print.attribute.standard.MediaSize;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StageDirectorDAO extends AbstractDAO<StageDirector> {
    private final static String SELECT_STAGE_DIRECTOR = "SELECT STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM STAGE_DIRECTOR";
    private final static String SELECT_STAGE_DIRECTOR_BY_ID = "SELECT STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM STAGE_DIRECTOR WHERE STAGE_DIRECTOR_ID = ?";
    private final static String DELETE_STAGE_DIRECTOR = "DELETE FROM STAGE_DIRECTOR WHERE STAGE_DIRECTOR_ID = ?";
    private final static String INSERT_STAGE_DIRECTOR = "INSERT INTO STAGE_DIRECTOR(STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO) VALUES(?,?,?,?)";
    private final static String SELECT_FILM_STAGE_DIRECTOR = "SELECT sd.STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM STAGE_DIRECTOR sd JOIN FILM_HAS_STAGE_DIRECTOR fhs ON fhs.STAGE_DIRECTOR_ID = sd.STAGE_DIRECTOR_ID WHERE fhs.FILM_ID = ?";

    private final static String STAGE_DIRECTOR_ID = "STAGE_DIRECTOR_ID";
    private final static String NAME = "NAME";
    private final static String DATE_OF_BIRTH = "DATE_OF_BIRTH";
    private final static String INFO = "INFO";

    public StageDirectorDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<StageDirector> findAll() throws DAOException {
        List<StageDirector> stageDirectors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STAGE_DIRECTOR, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                StageDirector stageDirector = new StageDirector(resultSet.getInt(STAGE_DIRECTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
                stageDirectors.add(stageDirector);
            }
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirectors;
    }

    @Override
    public StageDirector findEntityById(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        StageDirector stageDirector = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STAGE_DIRECTOR_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            stageDirector = new StageDirector(resultSet.getInt(STAGE_DIRECTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirector;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return super.deleteHandler(id, DELETE_STAGE_DIRECTOR);
    }

    @Override
    public boolean create(StageDirector stageDirector) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_STAGE_DIRECTOR, connection)) {
            preparedStatement.setInt(1, stageDirector.getStageDirectorId());
            preparedStatement.setString(2, stageDirector.getName());
            preparedStatement.setDate(3, (Date) stageDirector.getDateOfBirth());
            preparedStatement.setString(4, stageDirector.getInfo());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    @Override
    public List<StageDirector> findFilmEntity(int filmId) throws DAOException {
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
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirectors;
    }
}
