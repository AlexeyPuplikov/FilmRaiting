package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.exception.DAOException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO extends AbstractDAO<Film> {
    private final static String SELECT_FILMS = "SELECT FILM_ID, STAGE_DIRECTOR_ID, NAME, YEAR, DESCRIPTION, PREMIERE, TIME, BUDGET, COVER FROM FILM";
    private final static String SELECT_FILM_BY_ID = "SELECT FILM_ID, STAGE_DIRECTOR_ID, NAME, YEAR, DESCRIPTION, PREMIERE, TIME, BUDGET, COVER FROM FILM WHERE FILM_ID = ?";
    private final static String SELECT_FILM_BY_NAME = "SELECT FILM_ID, STAGE_DIRECTOR_ID, NAME, YEAR, DESCRIPTION, PREMIERE, TIME, BUDGET, COVER FROM FILM WHERE NAME = ?";
    private final static String DELETE_FILM = "DELETE FROM FILM WHERE FILM_ID = ?";
    private final static String INSERT_FILM = "INSERT INTO FILM(FILM_ID, STAGE_DIRECTOR_ID, NAME, YEAR, DESCRIPTION, PREMIERE, TIME, BUDGET) VALUES(?,?,?,?,?,?,?,?)";
    private final static String ADD_ACTORS_TO_FILM = "INSERT INTO FILM_HAS_ACTOR(FILM_ID, ACTOR_ID) VALUES (?,?)";
    private final static String ADD_GENRES_TO_FILM = "INSERT INTO FILM_HAS_GENRE(FILM_ID, GENRE_ID) VALUES (?,?)";
    private final static String ADD_COUNTRIES_TO_FILM = "INSERT INTO FILM_HAS_COUNTRY(FILM_ID, COUNTRY_ID) VALUES (?,?)";
    private final static String ADD_COVER_TO_FILM = "UPDATE FILM SET COVER = ? WHERE FILM_ID = ?";
    private final static String SELECT_FILM_BY_ACTOR = "SELECT F.FILM_ID, F.NAME, YEAR, DESCRIPTION, PREMIERE, TIME, BUDGET FROM FILM F "
            + "JOIN FILM_HAS_ACTOR FHA ON FHA.FILM_ID = F.FILM_ID AND ACTOR_ID = ?";
    private final static String SELECT_FILM_BY_STAGE_DIRECTOR = "SELECT F.FILM_ID, SD.NAME, F.NAME, YEAR, DESCRIPTION, PREMIERE, TIME, BUDGET FROM FILM F "
            + "JOIN STAGE_DIRECTOR SD ON SD.STAGE_DIRECTOR_ID = F.STAGE_DIRECTOR_ID WHERE STAGE_DIRECTOR_ID = ?";
    private final static String SELECT_FILM_BY_GENRE = "SELECT F.FILM_ID, F.NAME, YEAR, DESCRIPTION, PREMIERE, TIME FROM FILM F "
            + "JOIN FILM_HAS_GENRE FHG ON FHG.FILM_ID = F.FILM_ID AND GENRE_ID = ?";
    private final static String SELECT_FILM_BY_COUNTRY = "SELECT F.FILM_ID, F.NAME, YEAR, COUNTRY, DESCRIPTION, PREMIERE, TIME FROM FILM F "
            + "JOIN FILM_HAS_COUNTRY FHC ON FHC.FILM_ID = F.FILM_ID AND COUNTRY_ID = ?";

    private final static String SELECT_FILM_RATING = "SELECT AVG(MARK) AS RATING FROM RATING WHERE FILM_ID = ?";
    private final static String SET_RATING = "CALL SET_RATING(?,?,?)";
    private final static String SELECT_USER_MARK = "SELECT RATING_ID, USER_ID, FILM_ID, MARK FROM RATING WHERE USER_ID = ? AND FILM_ID = ?";
    private final static String SELECT_COVER = "SELECT COVER FROM FILM WHERE FILM_ID = ?";

    private final static String FILM_ID = "FILM_ID";
    private final static String NAME = "NAME";
    private final static String YEAR = "YEAR";
    private final static String DESCRIPTION = "DESCRIPTION";
    private final static String PREMIERE = "PREMIERE";
    private final static String TIME = "TIME";
    private final static String RATING = "RATING";
    private final static String RATING_ID = "RATING_ID";
    private final static String USER_ID = "USER_ID";
    private final static String MARK = "MARK";
    private final static String BUDGET = "BUDGET";
    private final static String COVER = "COVER";

    public FilmDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Film> findAll() throws DAOException {
        List<Film> films = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILMS, connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                films.add(setFilmFields(resultSet));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findAll films method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return films;
    }

    @Override
    public Film findEntityBySign(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Film film = new Film();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                film = setFilmFields(resultSet);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findFilmBySign method", ex);
        } finally {
            this.closeConnection(connection);
        }
        return film;
    }

    @Override
    public Film findEntityBySign(String name) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Film film = new Film();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_BY_NAME, connection)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                film = setFilmFields(resultSet);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findFilmByName method", ex);
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
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_FILM, connection)) {
            preparedStatement.setInt(1, film.getFilmId());
            preparedStatement.setInt(2, film.getStageDirector().getStageDirectorId());
            preparedStatement.setString(3, film.getName());
            preparedStatement.setInt(4, film.getYear());
            preparedStatement.setString(5, film.getDescription());
            preparedStatement.setDate(6, new java.sql.Date(film.getPremiere().getTime()));
            preparedStatement.setInt(7, film.getTime());
            preparedStatement.setInt(8, film.getBudget());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing create film method", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Film> findEntitiesByFilm(int filmId) throws DAOException {
        return null;
    }

    public boolean addActorsToFilm(Film film, Actor actor) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(ADD_ACTORS_TO_FILM, connection)) {
            preparedStatement.setInt(1, film.getFilmId());
            preparedStatement.setInt(2, actor.getActorId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing addActorsToFilm method", ex);
        }
    }

    public boolean addGenresToFilm(Film film, Genre genre) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(ADD_GENRES_TO_FILM, connection)) {
            preparedStatement.setInt(1, film.getFilmId());
            preparedStatement.setInt(2, genre.getGenreId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing addGenresToFilm method", ex);
        }
    }

    public boolean addCountriesToFilm(Film film, Country country) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(ADD_COUNTRIES_TO_FILM, connection)) {
            preparedStatement.setInt(1, film.getFilmId());
            preparedStatement.setInt(2, country.getCountryId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing addCountriesToFilm method", ex);
        }
    }

    public boolean addCoverToFilm(InputStream inputStream, Film film) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(ADD_COVER_TO_FILM, connection)) {
            preparedStatement.setBinaryStream(1, inputStream);
            preparedStatement.setInt(2, film.getFilmId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing addCoverToFilm method", ex);
        }
    }

    public void loadCoverToFile(Film film, String fileName) throws DAOException {
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet;
        File file = new File(fileName);
        InputStream input = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_COVER, connection);
             FileOutputStream output = new FileOutputStream(file)) {
            preparedStatement.setInt(1, film.getFilmId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                input = resultSet.getBinaryStream(COVER);
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
            }
            resultSet.close();
        } catch (SQLException | IOException ex) {
            throw new DAOException("Error while executing addCoverToFilm method", ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Film> findFilmByActor(int actorId) throws DAOException {
        return this.findHandler(actorId, SELECT_FILM_BY_ACTOR);
    }

    public List<Film> findFilmByStageDirector(int stageDirectorId) throws DAOException {
        return this.findHandler(stageDirectorId, SELECT_FILM_BY_STAGE_DIRECTOR);
    }

    public List<Film> findFilmByGenre(int genreId) throws DAOException {
        return this.findHandler(genreId, SELECT_FILM_BY_GENRE);
    }

    public List<Film> findFilmByCountry(int countryId) throws DAOException {
        return this.findHandler(countryId, SELECT_FILM_BY_COUNTRY);
    }

    private List<Film> findHandler(int entityId, String sql) throws DAOException {
        List<Film> films = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(sql, connection)) {
            preparedStatement.setInt(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                films.add(setFilmFields(resultSet));
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findHandler method", ex);
        }
        return films;
    }

    private static Film setFilmFields(ResultSet resultSet) throws DAOException {
        StageDirectorDAO stageDirectorDAO = new StageDirectorDAO();
        GenreDAO genreDAO = new GenreDAO();
        ActorDAO actorDAO = new ActorDAO();
        CountryDAO countryDAO = new CountryDAO();
        CommentDAO commentDAO = new CommentDAO();
        try {
            Film film = new Film();
            film.setId(resultSet.getInt(FILM_ID));
            film.setStageDirector(stageDirectorDAO.findEntityByFilm(resultSet.getInt(FILM_ID)));
            film.setName(resultSet.getString(NAME));
            film.setYear(resultSet.getInt(YEAR));
            film.setDescription(resultSet.getString(DESCRIPTION));
            film.setPremiere(resultSet.getDate(PREMIERE));
            film.setTime(resultSet.getInt(TIME));
            film.setBudget(resultSet.getInt(BUDGET));
            film.setCover(resultSet.getBlob(COVER));
            film.setCountries(countryDAO.findEntitiesByFilm(resultSet.getInt(FILM_ID)));
            film.setGenres(genreDAO.findEntitiesByFilm(resultSet.getInt(FILM_ID)));
            film.setActors(actorDAO.findEntitiesByFilm(resultSet.getInt(FILM_ID)));
            film.setComments(commentDAO.findEntitiesByFilm(resultSet.getInt(FILM_ID)));
            return film;
        } catch (SQLException ex) {
            throw new DAOException("Error while executing setFilmFields method", ex);
        }
    }

    public double findFilmRating(int filmId) throws DAOException {
        double rating = 0;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_RATING, connection)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rating = resultSet.getDouble(RATING);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findFilmRating method", ex);
        }
        return rating;
    }

    public boolean setFilmRating(Rating rating) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SET_RATING, connection)) {
            preparedStatement.setInt(1, rating.getUserId());
            preparedStatement.setInt(2, rating.getFilmId());
            preparedStatement.setInt(3, rating.getMark());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing setFilmRating method", ex);
        }
    }

    public Rating findUserMarkToFilm(int userId, int filmId) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_USER_MARK, connection)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Rating rating = new Rating();
                rating.setRatingId(resultSet.getInt(RATING_ID));
                rating.setUserId(resultSet.getInt(USER_ID));
                rating.setFilmId(resultSet.getInt(FILM_ID));
                rating.setMark(resultSet.getInt(MARK));
                return rating;
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findUserMarkToFilm method", ex);
        }
        return null;
    }
}
