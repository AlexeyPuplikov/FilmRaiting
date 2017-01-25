package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO extends AbstractDAO<Actor> {
    private final static String SELECT_ACTOR = "SELECT ACTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM ACTOR";
    private final static String SELECT_ACTOR_BY_ID = "SELECT ACTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM ACTOR WHERE ACTOR_ID = ?";
    private final static String DELETE_ACTOR = "DELETE FROM ACTOR WHERE ACTOR_ID = ?";
    private final static String INSERT_ACTOR = "INSERT INTO ACTOR(ACTOR_ID, NAME, DATE_OF_BIRTH, INFO) VALUES(?,?,?,?)";
    private final static String SELECT_FILM_ACTOR = "SELECT a.ACTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM ACTOR a JOIN FILM_HAS_ACTOR fha ON fha.ACTOR_ID = a.ACTOR_ID WHERE fha.FILM_ID = ?";

    private final static String ACTOR_ID = "ACTOR_ID";
    private final static String NAME = "NAME";
    private final static String DATE_OF_BIRTH = "DATE_OF_BIRTH";
    private final static String INFO = "INFO";

    public ActorDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Actor> findAll() throws DAOException, ConnectionPoolException {
        List<Actor> actors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_ACTOR, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Actor actor = new Actor(resultSet.getInt(ACTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
                actors.add(actor);
            }
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actors;
    }

    @Override
    public Actor findEntityById(int id) throws DAOException, ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        Actor actor = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_ACTOR_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            actor = new Actor(resultSet.getInt(ACTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actor;
    }

    @Override
    public boolean delete(int id) throws DAOException, ConnectionPoolException {
        return super.deleteHandler(id, DELETE_ACTOR);
    }

    @Override
    public boolean create(Actor actor) throws DAOException, ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_ACTOR, connection)) {
            preparedStatement.setInt(1, actor.getActorId());
            preparedStatement.setString(2, actor.getName());
            preparedStatement.setDate(3, (Date) actor.getDateOfBirth());
            preparedStatement.setString(4, actor.getInfo());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Actor> findFilmEntity(int filmID) throws DAOException, ConnectionPoolException {
        List<Actor> actors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_ACTOR, connection)) {
            preparedStatement.setInt(1, filmID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Actor actor = new Actor(resultSet.getInt(ACTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
                actors.add(actor);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actors;
    }
}
