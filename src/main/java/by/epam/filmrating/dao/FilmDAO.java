package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO extends AbstractDAO<Film> {
    private final static String SELECT_FILM = "SELECT * FROM film";
    private final static String SELECT_FILM_BY_ID = "SELECT * FROM film WHERE FILM_ID = ?";
    private final static String DELETE_FILM = "DELETE FROM film WHERE FILM_ID = ?";
    private final static String INSERT_FILM = "INSERT INTO film(COMMENT_ID, TEXT, FILM_ID, USER_ID) VALUES(?,?,?,?)";

    public FilmDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Film> findAll() throws ConnectionPoolException {
        List<Film> films = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        StageDirectorDAO stageDirectorDAO = new StageDirectorDAO();
        GenreDAO genreDAO = new GenreDAO();
        ActorDAO actorDAO = new ActorDAO();
        CommentDAO commentDAO = new CommentDAO();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Film film = new Film(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getInt(7),
                        stageDirectorDAO.findFilmEntity(resultSet.getInt(1)), genreDAO.findFilmEntity(resultSet.getInt(1)),
                        actorDAO.findFilmEntity(resultSet.getInt(1)), commentDAO.findFilmEntity(resultSet.getInt(1)));
                films.add(film);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return films;
    }

    @Override
    public Film findEntityById(int id) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        Film film = null;
        StageDirectorDAO stageDirectorDAO = new StageDirectorDAO();
        GenreDAO genreDAO = new GenreDAO();
        ActorDAO actorDAO = new ActorDAO();
        CommentDAO commentDAO = new CommentDAO();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            film = new Film(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getInt(7),
                    stageDirectorDAO.findFilmEntity(resultSet.getInt(1)), genreDAO.findFilmEntity(resultSet.getInt(1)),
                    actorDAO.findFilmEntity(resultSet.getInt(1)), commentDAO.findFilmEntity(resultSet.getInt(1)));
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return film;
    }

    @Override
    public boolean delete(int id) throws ConnectionPoolException {
        return super.deleteHandler(id, DELETE_FILM);
    }

    @Override
    public boolean create(Film entity) throws ConnectionPoolException {
        return false;
    }

    @Override
    public Film update(Film entity) {
        return null;
    }

    @Override
    public List<Film> findFilmEntity(int filmId) throws ConnectionPoolException {
        return null;
    }

}