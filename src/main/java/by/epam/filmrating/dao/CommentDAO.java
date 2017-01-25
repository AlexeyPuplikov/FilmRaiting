package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.exception.ConnectionPoolException;
import by.epam.filmrating.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO extends AbstractDAO<Comment> {
    private final static String DELETE_COMMENT = "DELETE FROM COMMENT WHERE COMMENT_ID = ?";
    private final static String INSERT_COMMENT = "INSERT INTO COMMENT(COMMENT_ID, TEXT, FILM_ID, USER_ID) VALUES(?,?,?,?)";
    private final static String SELECT_FILM_COMMENT = "SELECT COMMENT_ID, TEXT, FILM_ID, USER_ID FROM COMMENT WHERE FILM_ID = ?";

    private final static String COMMENT_ID = "COMMENT_ID";
    private final static String TEXT = "TEXT";
    private final static String FILM_ID = "FILM_ID";
    private final static String USER_ID = "USER_ID";


    public CommentDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public boolean delete(int id) throws DAOException, ConnectionPoolException {
        return super.deleteHandler(id, DELETE_COMMENT);
    }

    @Override
    public boolean create(Comment comment) throws DAOException, ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_COMMENT, connection)) {
            preparedStatement.setInt(1, comment.getCommentId());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setInt(3, comment.getFilmId());
            preparedStatement.setInt(4, comment.getUserId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        }
    }

    @Override
    public List<Comment> findFilmEntity(int filmId) throws DAOException, ConnectionPoolException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_COMMENT, connection)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(resultSet.getInt(COMMENT_ID), resultSet.getString(TEXT), resultSet.getInt(FILM_ID), resultSet.getInt(USER_ID));
                comments.add(comment);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DAOException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return comments;
    }

    @Override
    public List<Comment> findAll() throws DAOException {
        return null;
    }

    @Override
    public Comment findEntityById(int id) throws DAOException {
        return null;
    }
}
