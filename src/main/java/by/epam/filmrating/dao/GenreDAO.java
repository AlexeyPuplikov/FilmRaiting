package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends AbstractDAO<Genre> {
    private final static String SELECT_GENRES = "SELECT GENRE_ID, NAME FROM GENRE";
    private final static String SELECT_GENRE_BY_ID = "SELECT GENRE_ID, NAME FROM GENRE WHERE GENRE_ID = ?";
    private final static String SELECT_GENRE_BY_NAME = "SELECT GENRE_ID, NAME FROM GENRE WHERE NAME = ?";
    private final static String DELETE_GENRE = "DELETE FROM GENRE WHERE GENRE_ID = ?";
    private final static String INSERT_GENRE = "INSERT INTO GENRE(GENRE_ID, NAME) VALUES(?,?)";
    private final static String SELECT_GENRES_BY_FILM = "SELECT G.GENRE_ID, NAME FROM GENRE G JOIN FILM_HAS_GENRE FHG ON FHG.GENRE_ID = G.GENRE_ID WHERE FHG.FILM_ID = ?";
    private final static String SELECT_GENRES_NOT_IN_FILM = "SELECT DISTINCT G.GENRE_ID, NAME FROM GENRE G LEFT JOIN FILM_HAS_GENRE FHG ON FHG.GENRE_ID = G.GENRE_ID WHERE G.GENRE_ID NOT IN (SELECT G.GENRE_ID FROM GENRE G JOIN FILM_HAS_GENRE FHG ON FHG.GENRE_ID = G.GENRE_ID WHERE FHG.FILM_ID = ?)";

    private final static String GENRE_ID = "GENRE_ID";
    private final static String NAME = "NAME";

    public GenreDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Genre> findAll() throws DAOException {
        List<Genre> genres = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_GENRES, connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Genre genre = new Genre(resultSet.getInt(GENRE_ID), resultSet.getString(NAME));
                genres.add(genre);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findAll genre method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return genres;
    }

    @Override
    public Genre findEntityBySign(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Genre genre = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_GENRE_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                genre = new Genre(resultSet.getInt(GENRE_ID), resultSet.getString(NAME));
            }
            resultSet.close();
            return genre;
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findGenreBySign method", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public Genre findEntityBySign(String name) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Genre genre = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_GENRE_BY_NAME, connection)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                genre = new Genre(resultSet.getInt(GENRE_ID), resultSet.getString(NAME));
            }
            resultSet.close();
            return genre;
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findGenreBySign method", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return super.deleteHandler(id, DELETE_GENRE);
    }

    @Override
    public boolean create(Genre genre) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_GENRE, connection)) {
            preparedStatement.setInt(1, genre.getGenreId());
            preparedStatement.setString(2, genre.getName());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing create genre method", ex);
        }
    }

    @Override
    public List<Genre> findEntitiesByFilm(int filmId) throws DAOException {
        return this.findEntitiesByFilmHandler(filmId, SELECT_GENRES_BY_FILM);
    }

    public List<Genre> findEntitiesNotInFilm(int filmId) throws DAOException {
       return this.findEntitiesByFilmHandler(filmId, SELECT_GENRES_NOT_IN_FILM);
    }

    private List<Genre> findEntitiesByFilmHandler(int filmId, String sql) throws DAOException {
        List<Genre> genres = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(sql, connection)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre(resultSet.getInt(GENRE_ID), resultSet.getString(NAME));
                genres.add(genre);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findEntitiesByFilmHandler method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return genres;
    }
}
