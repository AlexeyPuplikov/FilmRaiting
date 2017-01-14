package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO extends AbstractDAO{

    private final static String SELECT_ACTOR_FILM = "SELECT * FROM actor";

    public ActorDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List findAll() {
        List<Actor> actors = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = connectionPool.getConnection();
        try {
            preparedStatement = connectionPool.getPreparedStatement(SELECT_ACTOR_FILM, connection);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Actor actor = new Actor(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getString(4));
                actors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return actors;
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
