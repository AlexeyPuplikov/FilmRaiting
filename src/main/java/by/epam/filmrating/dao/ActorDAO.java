package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO extends AbstractDAO<Actor> {
    private final static String SELECT_ACTORS = "SELECT ACTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM ACTOR";
    private final static String SELECT_ACTOR_BY_ID = "SELECT ACTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM ACTOR WHERE ACTOR_ID = ?";
    private final static String SELECT_ACTOR_BY_NAME = "SELECT ACTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM ACTOR WHERE NAME = ?";
    private final static String DELETE_ACTOR = "DELETE FROM ACTOR WHERE ACTOR_ID = ?";
    private final static String INSERT_ACTOR = "INSERT INTO ACTOR(ACTOR_ID, NAME, DATE_OF_BIRTH, INFO) VALUES(?,?,?,?)";
    private final static String SELECT_ACTORS_BY_FILM = "SELECT A.ACTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM ACTOR A JOIN FILM_HAS_ACTOR FHA ON FHA.ACTOR_ID = A.ACTOR_ID WHERE FHA.FILM_ID = ?";
    private final static String SELECT_ACTORS_NOT_IN_FILM = "SELECT DISTINCT A.ACTOR_ID, NAME, DATE_OF_BIRTH, INFO FROM ACTOR A LEFT JOIN FILM_HAS_ACTOR FHA ON FHA.ACTOR_ID = A.ACTOR_ID "
            + "WHERE A.ACTOR_ID NOT IN (SELECT A.ACTOR_ID FROM ACTOR A JOIN FILM_HAS_ACTOR FHA ON FHA.ACTOR_ID = A.ACTOR_ID WHERE FHA.FILM_ID = ?)";

    private final static String ACTOR_ID = "ACTOR_ID";
    private final static String NAME = "NAME";
    private final static String DATE_OF_BIRTH = "DATE_OF_BIRTH";
    private final static String INFO = "INFO";

    public ActorDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Actor> findAll() throws DAOException {
        List<Actor> actors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_ACTORS, connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Actor actor = new Actor(resultSet.getInt(ACTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
                actors.add(actor);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findAllActors method.", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actors;
    }

    @Override
    public Actor findEntityBySign(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Actor actor = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_ACTOR_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    actor = new Actor(resultSet.getInt(ACTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findActorBySign method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actor;
    }

    @Override
    public Actor findEntityBySign(String name) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Actor actor = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_ACTOR_BY_NAME, connection)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    actor = new Actor(resultSet.getInt(ACTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findEntityBySign method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actor;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return super.deleteHandler(id, DELETE_ACTOR);
    }

    @Override
    public boolean create(Actor actor) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_ACTOR, connection)) {
            preparedStatement.setInt(1, actor.getActorId());
            preparedStatement.setString(2, actor.getName());
            preparedStatement.setDate(3, new Date(actor.getDateOfBirth().getTime()));
            preparedStatement.setString(4, actor.getInfo());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing create actor method", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Actor> findEntitiesByFilm(int filmId) throws DAOException {
        return this.findEntitiesByFilmHandler(filmId, SELECT_ACTORS_BY_FILM);
    }

    public List<Actor> findEntitiesNotInFilm(int filmId) throws DAOException {
        return this.findEntitiesByFilmHandler(filmId, SELECT_ACTORS_NOT_IN_FILM);
    }

    private List<Actor> findEntitiesByFilmHandler(int filmId, String sql) throws DAOException {
        List<Actor> actors = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(sql, connection)) {
            preparedStatement.setInt(1, filmId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Actor actor = new Actor(resultSet.getInt(ACTOR_ID), resultSet.getString(NAME), resultSet.getDate(DATE_OF_BIRTH), resultSet.getString(INFO));
                    actors.add(actor);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findActorsByFilmHandler method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return actors;
    }
}
