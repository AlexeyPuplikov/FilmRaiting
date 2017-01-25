package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.EnumGenre;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends AbstractDAO<Genre> {
    private final static String SELECT_GENRE = "SELECT GENRE_ID, NAME FROM GENRE";
    private final static String SELECT_GENRE_BY_ID = "SELECT GENRE_ID, NAME FROM GENRE WHERE GENRE_ID = ?";
    private final static String DELETE_GENRE = "DELETE FROM GENRE WHERE GENRE_ID = ?";
    private final static String INSERT_GENRE = "INSERT INTO GENRE(GENRE_ID, NAME) VALUES(?,?)";
    private final static String SELECT_FILM_GENRE = "SELECT g.GENRE_ID, NAME FROM GENRE g JOIN FILM_HAS_GENRE fhg ON fhg.GENRE_ID = g.GENRE_ID WHERE fhg.FILM_ID = ?";

    private final static String GENRE_ID = "GENRE_ID";
    private final static String NAME = "NAME";

    public GenreDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Genre> findAll() throws DAOException, ConnectionPoolException {
        List<Genre> genres = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_GENRE, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Genre genre = new Genre(resultSet.getInt(GENRE_ID), resultSet.getString(NAME));
                genres.add(genre);
            }
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return genres;
    }

    @Override
    public Genre findEntityById (int id) throws DAOException, ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        Genre genre = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_GENRE_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            genre = new Genre(resultSet.getInt(GENRE_ID), resultSet.getString(NAME));
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return genre;
    }

    @Override
    public boolean delete(int id) throws DAOException, ConnectionPoolException {
        return super.deleteHandler(id, DELETE_GENRE);
    }

    @Override
    public boolean create(Genre genre) throws DAOException, ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_GENRE, connection)) {
            preparedStatement.setInt(1, genre.getGenreId());
            preparedStatement.setString(2, String.valueOf(genre.getName()));
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    @Override
    public List<Genre> findFilmEntity(int filmId) throws DAOException, ConnectionPoolException {
        List<Genre> genres = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_GENRE, connection)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre(resultSet.getInt(GENRE_ID),resultSet.getString(NAME));
                genres.add(genre);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return genres;
    }
}
