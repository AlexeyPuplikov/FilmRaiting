package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.StageDirector;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StageDirectorDAO extends AbstractDAO<StageDirector> {
    private final static String SELECT_STAGE_DIRECTOR = "SELECT STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM STAGE_DIRECTOR";
    private final static String SELECT_STAGE_DIRECTOR_BY_ID = "SELECT STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM STAGE_DIRECTOR WHERE STAGE_DIRECTOR_ID = ?";
    private final static String SELECT_STAGE_DIRECTOR_BY_NAME = "SELECT STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM STAGE_DIRECTOR WHERE NAME = ?";
    private final static String DELETE_STAGE_DIRECTOR = "DELETE FROM STAGE_DIRECTOR WHERE STAGE_DIRECTOR_ID = ?";
    private final static String INSERT_STAGE_DIRECTOR = "INSERT INTO STAGE_DIRECTOR(STAGE_DIRECTOR_ID, NAME, DATE_OF_BIRTH, INFO) VALUES(?,?,?,?)";
    private final static String SELECT_STAGE_DIRECTOR_BY_FILM = "SELECT SD.STAGE_DIRECTOR_ID, SD.NAME, DATE_OF_BIRTH, INFO FROM STAGE_DIRECTOR SD JOIN FILM F ON SD.STAGE_DIRECTOR_ID = F.STAGE_DIRECTOR_ID WHERE F.FILM_ID = ?";

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
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STAGE_DIRECTOR, connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                StageDirector stageDirector = new StageDirector(resultSet.getInt(STAGE_DIRECTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
                stageDirectors.add(stageDirector);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findAll stageDirectors method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirectors;
    }

    @Override
    public StageDirector findEntityBySign(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        StageDirector stageDirector = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STAGE_DIRECTOR_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stageDirector = new StageDirector(resultSet.getInt(STAGE_DIRECTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findEntityBySign method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirector;
    }

    @Override
    public StageDirector findEntityBySign(String name) throws DAOException {
        Connection connection = connectionPool.getConnection();
        StageDirector stageDirector = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STAGE_DIRECTOR_BY_NAME, connection)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stageDirector = new StageDirector(resultSet.getInt(STAGE_DIRECTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findEntityBySign method", ex);
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
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_STAGE_DIRECTOR, connection)) {
            preparedStatement.setInt(1, stageDirector.getStageDirectorId());
            preparedStatement.setString(2, stageDirector.getName());
            preparedStatement.setDate(3, new java.sql.Date(stageDirector.getDateOfBirth().getTime()));
            preparedStatement.setString(4, stageDirector.getInfo());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing create method", ex);
        }
    }

    @Override
    public List<StageDirector> findEntitiesByFilm(int id) throws DAOException {
        return null;
    }

    public StageDirector findEntityByFilm(int filmId) throws DAOException {
        StageDirector stageDirector = null;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_STAGE_DIRECTOR_BY_FILM, connection)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stageDirector = new StageDirector(resultSet.getInt(STAGE_DIRECTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findStageDirectorByFilm method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return stageDirector;
    }
}
