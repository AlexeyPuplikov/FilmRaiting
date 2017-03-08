package by.epam.filmrating.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class DBConnectionPool {
    private final static String DB_POOL_SIZE = "db.poolSize";
    private final static String DB_DRIVER_CLASS = "db.driver";
    private final static String DB_URL = "db.url";
    private final static String DB_USER = "db.user";
    private final static String DB_PASSWORD = "db.password";
    private final static String DB_PROPERTY_PATH = "properties.database";

    private BlockingQueue<Connection> connectionList;
    private String DBUrl;
    private String DBUser;
    private String DBPassword;

    private DBConnectionPool() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY_PATH);

            DBUrl = resourceBundle.getString(DB_URL);
            DBUser = resourceBundle.getString(DB_USER);
            DBPassword = resourceBundle.getString(DB_PASSWORD);
            int poolSize = Integer.parseInt(resourceBundle.getString(DB_POOL_SIZE));
            connectionList = new ArrayBlockingQueue<>(poolSize);
            Class.forName(resourceBundle.getString(DB_DRIVER_CLASS));

            for (int i = 0; i < poolSize; i++) {
                connectionList.add(DriverManager.getConnection(DBUrl, DBUser, DBPassword));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException("Failed to create instance of ConnectionPool.", ex);
        }
    }

    private static class PoolHolder {
        private static final DBConnectionPool HOLDER_INSTANCE = new DBConnectionPool();
    }

    public static DBConnectionPool getInstance() {
        return PoolHolder.HOLDER_INSTANCE;
    }

    public Connection getConnection() {
        Connection connection;
        try {
            connection = connectionList.poll(15, TimeUnit.SECONDS);
            if (connection == null) {
                connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
                connectionList.add(connection);
            }
        } catch (InterruptedException | SQLException ex) {
            throw new RuntimeException("Error while creating connection.", ex);
        }
        return connection;
    }

    public void freeConnection(Connection connection) {
        try {
            if (connection != null) {
                if (!connectionList.offer(connection, 15, TimeUnit.SECONDS)) {
                    connection.close();
                }
            }
        } catch (InterruptedException | SQLException ex) {
            throw new RuntimeException("Error while return connection.", ex);
        }
    }

    public PreparedStatement getPreparedStatement(String sql, Connection connection) {
        try {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                if (preparedStatement != null) {
                    return preparedStatement;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error while getting prepared statement.", ex);
        }
        throw new RuntimeException("Error while getting prepared statement.");
    }
}
