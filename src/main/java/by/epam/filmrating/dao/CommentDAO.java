package by.epam.filmrating.dao;

import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO extends AbstractDAO<Comment> {
    private final static String SELECT_COMMENT = "SELECT * FROM comment";
    private final static String SELECT_COMMENT_BY_ID = "SELECT * FROM comment WHERE COMMENT_ID = ?";
    private final static String DELETE_COMMENT = "DELETE FROM comment WHERE COMMENT_ID = ?";
    private final static String INSERT_COMMENT = "INSERT INTO comment(COMMENT_ID, TEXT, FILM_ID, USER_ID) VALUES(?,?,?,?)";
    private final static String SELECT_FILM_COMMENT = "SELECT * FROM comment WHERE FILM_ID = ?";

    public CommentDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Comment> findAll() throws ConnectionPoolException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_COMMENT, connection);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Comment comment = new Comment(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4));
                comments.add(comment);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return comments;
    }

    @Override
    public Comment findEntityById(int id) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        Comment comment = null;
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_COMMENT_BY_ID, connection)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            comment = new Comment(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4));
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public boolean delete(int id) throws ConnectionPoolException {
        return super.deleteHandler(id, DELETE_COMMENT);
    }

    @Override
    public boolean create(Comment comment) throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(INSERT_COMMENT, connection)) {
            preparedStatement.setInt(1, comment.getCommentId());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setInt(3, comment.getFilmId());
            preparedStatement.setInt(4, comment.getUserId());
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        }
    }

    @Override
    public List<Comment> findFilmEntity(int filmId) throws ConnectionPoolException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connectionPool.getPreparedStatement(SELECT_FILM_COMMENT, connection)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4));
                comments.add(comment);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new ConnectionPoolException("", ex);
        } finally {
            this.closeConnection(connection);
        }
        return comments;
    }

    @Override
    public Comment update(Comment entity) {
        return null;
    }
}
