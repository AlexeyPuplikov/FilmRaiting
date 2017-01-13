package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Entity;
import by.epam.filmrating.entity.Film;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO extends AbstractDAO{
    private final static String SELECT_ALL_FILM = "SELECT * FROM film";

    public FilmDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connectionPool.getPreparedStatement(SELECT_ALL_FILM);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Film film = new Film(resultSet.getInt("FILM_ID"), resultSet.getString("NAME"), resultSet.getDate("YEAR"),
                        resultSet.getString("COUNTRY"), resultSet.getString("DESCRIPTION"), resultSet.getDate("PREMIERE"),
                        resultSet.getInt("TIME"));
                films.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
        }
        return films;
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
