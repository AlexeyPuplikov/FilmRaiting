package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO extends AbstractDAO<Actor> {

    private final static String SELECT_ACTOR = "SELECT * FROM actor";
    private final static String SELECT_ACTOR_BY_ID = "SELECT * FROM actor WHERE ACTOR_ID = ?";
    private final static String DELETE_ACTOR = "DELETE FROM actor WHERE ACTOR_ID = ?";
    private final static String INSERT_ACTOR = "INSERT INTO actor(ACTOR_ID, NAME, DATE_OF_BIRTH, INFO) VALUES(?,?,?,?)";
    private final static String SELECT_FILM_ACTOR = "SELECT * FROM actor a\n" +
            "JOIN film_has_actor fha\n" +
            "ON fha.ACTOR_ID = a.ACTOR_ID\n" +
            "WHERE fha.FILM_ID = ?";

    public ActorDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Actor> findAll() throws ConnectionPoolException {
        List<Actor> actors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_ACTOR, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Actor actor = new Actor(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4));
                actors.add(actor);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actors;
    }

    @Override
    public Actor findEntityById(int id) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        Actor actor = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_ACTOR_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            actor = new Actor(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4));
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actor;
    }

    @Override
    public boolean delete(int id) throws ConnectionPoolException {
        return super.deleteHandler(id, DELETE_ACTOR);
    }

    @Override
    public boolean create(Actor actor) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_ACTOR, connection)) {
            preparedStatement.setInt(1, actor.getActorId());
            preparedStatement.setString(2, actor.getName());
            preparedStatement.setDate(3, (Date) actor.getDateOfBirth());
            preparedStatement.setString(4, actor.getInfo());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        }
    }

    @Override
    public List<Actor> findFilmEntity(int filmID) throws ConnectionPoolException {
        List<Actor> actors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_ACTOR, connection)) {
            preparedStatement.setInt(1, filmID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Actor actor = new Actor(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4));
                actors.add(actor);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actors;
    }

    @Override
    public Actor update(Actor entity) {
        return null;
    }

}
