package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO extends AbstractDAO<Country> {
    private final static String SELECT_COUNTRIES = "SELECT COUNTRY_ID, NAME FROM COUNTRY";
    private final static String SELECT_COUNTRY_BY_ID = "SELECT COUNTRY_ID, NAME FROM COUNTRY WHERE COUNTRY_ID = ?";
    private final static String SELECT_COUNTRY_BY_NAME = "SELECT COUNTRY_ID, NAME FROM COUNTRY WHERE NAME = ?";
    private final static String DELETE_COUNTRY = "DELETE FROM COUNTRY WHERE COUNTRY_ID = ?";
    private final static String INSERT_COUNTRY = "INSERT INTO COUNTRY(COUNTRY_ID, NAME) VALUES(?,?)";
    private final static String SELECT_COUNTRIES_BY_FILM = "SELECT C.COUNTRY_ID, NAME FROM COUNTRY C JOIN FILM_HAS_COUNTRY FHC ON FHC.COUNTRY_ID = C.COUNTRY_ID WHERE FHC.FILM_ID = ?";
    private final static String SELECT_COUNTRIES_NOT_IN_FILM = "SELECT DISTINCT C.COUNTRY_ID, NAME FROM COUNTRY C LEFT JOIN FILM_HAS_COUNTRY FHC ON FHC.COUNTRY_ID = C.COUNTRY_ID "
            + "WHERE C.COUNTRY_ID NOT IN (SELECT C.COUNTRY_ID FROM COUNTRY C JOIN FILM_HAS_COUNTRY FHC ON FHC.COUNTRY_ID = C.COUNTRY_ID WHERE FHC.FILM_ID = ?)";

    private final static String COUNTRY_ID = "COUNTRY_ID";
    private final static String NAME = "NAME";

    public CountryDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Country> findAll() throws DAOException {
        List<Country> countries = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_COUNTRIES, connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Country country = new Country(resultSet.getInt(COUNTRY_ID), resultSet.getString(NAME));
                countries.add(country);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findAll countries method.", ex);
        } finally {
            this.closeConnection(connection);
        }
        return countries;
    }

    @Override
    public Country findEntityBySign(int id) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Country country = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_COUNTRY_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    country = new Country(resultSet.getInt(COUNTRY_ID), resultSet.getString(NAME));
                }
            }
            return country;
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findCountryBySign method.", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public Country findEntityBySign(String name) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Country country = null;
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_COUNTRY_BY_NAME, connection)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    country = new Country(resultSet.getInt(COUNTRY_ID), resultSet.getString(NAME));
                }
            }
            return country;
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findCountryBySign method.", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return super.deleteHandler(id, DELETE_COUNTRY);
    }

    @Override
    public boolean create(Country country) throws DAOException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_COUNTRY, connection)) {
            preparedStatement.setInt(1, country.getCountryId());
            preparedStatement.setString(2, country.getName());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("Error while executing create country method.", ex);
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Country> findEntitiesByFilm(int filmId) throws DAOException {
        return this.findEntitiesByFilmHandler(filmId, SELECT_COUNTRIES_BY_FILM);
    }

    public List<Country> findEntitiesNotInFilm(int filmId) throws DAOException {
        return this.findEntitiesByFilmHandler(filmId, SELECT_COUNTRIES_NOT_IN_FILM);
    }

    private List<Country> findEntitiesByFilmHandler(int filmId, String sql) throws DAOException {
        List<Country> countries = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connectionPool.getPreparedStatement(sql, connection)) {
            preparedStatement.setInt(1, filmId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Country country = new Country(resultSet.getInt(COUNTRY_ID), resultSet.getString(NAME));
                    countries.add(country);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while executing findCountriesByFilmHandler method.", ex);
        } finally {
            this.closeConnection(connection);
        }
        return countries;
    }
}
