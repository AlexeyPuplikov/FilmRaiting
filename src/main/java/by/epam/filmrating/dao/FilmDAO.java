package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO extends AbstractDAO<Film> {
    private final static String SELECT_FILM = "SELECT FILM_ID, NAME, YEAR, COUNTRY, DESCRIPTION, PREMIERE, TIME FROM FILM";
    private final static String SELECT_FILM_BY_ID = "SELECT FILM_ID, NAME, YEAR, COUNTRY, DESCRIPTION, PREMIERE, TIME FROM FILM WHERE FILM_ID = ?";
    private final static String DELETE_FILM = "DELETE FROM FILM WHERE FILM_ID = ?";
    private final static String INSERT_FILM = "INSERT INTO FILM(FILM_ID, NAME, YEAR, COUNTRY, DESCRIPTION, PREMIERE, TIME) VALUES(?,?,?,?,?,?,?)";
    private final static String ADD_STAGE_DIRECTOR_TO_FILM = "INSERT INTO FILM_HAS_STAGE_DIRECTOR(FILM_ID, STAGE_DIRECTOR_ID) VALUES (?,?)";
    private final static String ADD_ACTOR_TO_FILM = "INSERT INTO FILM_HAS_ACTOR(FILM_ID, ACTOR_ID) VALUES (?,?)";
    private final static String ADD_GENRE_TO_FILM = "INSERT INTO FILM_HAS_GENRE(FILM_ID, GENRE_ID) VALUES (?,?)";
    private final static String SELECT_FILM_BY_ACTOR = "SELECT f.FILM_ID, f.NAME, YEAR, COUNTRY, DESCRIPTION, PREMIERE, TIME FROM FILM f "
            + "JOIN FILM_HAS_ACTOR fha ON fha.FILM_ID = f.FILM_ID AND ACTOR_ID = ?";
    private final static String SELECT_FILM_BY_STAGE_DIRECTOR = "SELECT f.FILM_ID, f.NAME, YEAR, COUNTRY, DESCRIPTION, PREMIERE, TIME FROM FILM f "
            + "JOIN FILM_HAS_STAGE_DIRECTOR fhs ON fhs.FILM_ID = f.FILM_ID AND STAGE_DIRECTOR_ID = ?";
    private final static String SELECT_FILM_BY_GENRE = "SELECT f.FILM_ID, f.NAME, YEAR, COUNTRY, DESCRIPTION, PREMIERE, TIME FROM FILM f "
            + "JOIN FILM_HAS_GENRE fhg ON fhg.FILM_ID = f.FILM_ID AND GENRE_ID = ?";
    private final static String SELECT_FILM_RATING = "SELECT AVG(MARK) AS RATING FROM RATING WHERE FILM_ID = ?";
    private final static String SET_RATING = "CALL SET_RATING(?,?,?)";
    private final static String SELECT_USER_MARK = "SELECT RATING_ID, USER_ID, FILM_ID, MARK FROM RATING WHERE USER_ID = ? AND FILM_ID = ?";

    private final static String FILM_ID = "FILM_ID";
    private final static String NAME = "NAME";
    private final static String YEAR = "YEAR";
    private final static String COUNTRY = "COUNTRY";
    private final static String DESCRIPTION = "DESCRIPTION";
    private final static String PREMIERE = "PREMIERE";
    private final static String TIME = "TIME";
    private final static String RATING = "RATING";
    private final static String RATING_ID = "RATING_ID";
    private final static String USER_ID = "USER_ID";
    private final static String MARK = "MARK";

    public FilmDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Film> findAll() throws DAOException {
        List<Film> films = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                films.add(setFilmFields(resultSet));
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return films;
    }

    @Override
    public Film findEntityById(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Film film = new Film();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            film = setFilmFields(resultSet);
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return film;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return super.deleteHandler(id, DELETE_FILM);
    }

    @Override
    public boolean create(Film film) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_FILM, connection)) {
            preparedStatement.setInt(1, film.getFilmId());
            preparedStatement.setString(2, film.getName());
            preparedStatement.setInt(3, film.getYear());
            preparedStatement.setString(4, film.getCountry());
            preparedStatement.setString(5, film.getDescription());
            preparedStatement.setDate(6, (Date) film.getPremiere());
            preparedStatement.setInt(7, film.getTime());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Film> findFilmEntity(int filmId) throws DAOException {
        return null;
    }

    public boolean addStageDirectorToFilm(Film film, StageDirector stageDirector) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(ADD_STAGE_DIRECTOR_TO_FILM, connection)) {
            preparedStatement.setInt(1, film.getFilmId());
            preparedStatement.setInt(2, stageDirector.getStageDirectorId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    public boolean addActorToFilm(Film film, Actor actor) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(ADD_ACTOR_TO_FILM, connection)) {
            preparedStatement.setInt(1, film.getFilmId());
            preparedStatement.setInt(2, actor.getActorId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    public boolean addGenreToFilm(Film film, Genre genre) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(ADD_GENRE_TO_FILM, connection)) {
            preparedStatement.setInt(1, film.getFilmId());
            preparedStatement.setInt(2, genre.getGenreId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    public List<Film> findFilmByActor(int actorId) throws DAOException {
        return this.findHelper(actorId, SELECT_FILM_BY_ACTOR);
    }

    public List<Film> findFilmByStageDirector(int stageDirectorId) throws DAOException {
        return this.findHelper(stageDirectorId, SELECT_FILM_BY_STAGE_DIRECTOR);
    }

    public List<Film> findFilmByGenre(int genreId) throws DAOException {
        return this.findHelper(genreId, SELECT_FILM_BY_GENRE);
    }

    private List<Film> findHelper(int entityId, String sql) throws DAOException {
        List<Film> films = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(sql, connection)) {
            preparedStatement.setInt(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                films.add(setFilmFields(resultSet));
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
        return films;
    }

    private static Film setFilmFields(ResultSet resultSet) throws DAOException {
        StageDirectorDAO stageDirectorDAO = new StageDirectorDAO();
        GenreDAO genreDAO = new GenreDAO();
        ActorDAO actorDAO = new ActorDAO();
        CommentDAO commentDAO = new CommentDAO();
        try {
            Film film = new Film();
            film.setId(resultSet.getInt(FILM_ID));
            film.setName(resultSet.getString(NAME));
            film.setYear(resultSet.getInt(YEAR));
            film.setCountry(resultSet.getString(COUNTRY));
            film.setDescription(resultSet.getString(DESCRIPTION));
            film.setPremiere(resultSet.getDate(PREMIERE));
            film.setTime(resultSet.getInt(TIME));
            film.setStageDirectors(stageDirectorDAO.findFilmEntity(resultSet.getInt(FILM_ID)));
            film.setGenres(genreDAO.findFilmEntity(resultSet.getInt(FILM_ID)));
            film.setActors(actorDAO.findFilmEntity(resultSet.getInt(FILM_ID)));
            film.setComments(commentDAO.findFilmEntity(resultSet.getInt(FILM_ID)));
            return film;
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    public double findFilmRating(int filmId) throws DAOException {
        double rating;
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_RATING, connection)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            rating = resultSet.getDouble(RATING);
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
        return rating;
    }

    public boolean setFilmRating(Rating rating) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SET_RATING, connection)) {
            preparedStatement.setInt(1, rating.getUserId());
            preparedStatement.setInt(2, rating.getFilmId());
            preparedStatement.setInt(3, rating.getMark());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    public Rating findUserMarkToFilm(int userId, int filmId) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER_MARK, connection)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Rating rating = new Rating();
            rating.setRatingId(resultSet.getInt(RATING_ID));
            rating.setUserId(resultSet.getInt(USER_ID));
            rating.setFilmId(resultSet.getInt(FILM_ID));
            rating.setMark(resultSet.getInt(MARK));
            return rating;
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }
}
