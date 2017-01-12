package by.epam.filmrating.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class DBConnectionPool {

    private final static String DB_POOL_SIZE = "db.poolsize";
    private final static String DB_DRIVER_CLASS = "db.driver";
    private final static String DB_URL = "db.url";
    private final static String DB_USER = "db.user";
    private final static String DB_PASSWORD = "db.password";
    private final static String DB_PROPERTY_PATH = "../database.properties";

    private BlockingQueue<Connection> connectionList;
    private String DBUrl;
    private String DBlogin;
    private String DBpassword;
    private int poolSize;

    private DBConnectionPool() {
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\user\\Desktop\\FinalProject\\FilmRaiting\\src\\main\\resources\\database.properties");
            Properties property = new Properties();
            property.load(fis);

            DBUrl = property.getProperty(DB_URL);
            DBlogin = property.getProperty(DB_USER);
            DBpassword = property.getProperty(DB_PASSWORD);
            poolSize = Integer.parseInt(property.getProperty(DB_POOL_SIZE));
            connectionList = new ArrayBlockingQueue<>(poolSize);
            Class.forName(property.getProperty(DB_DRIVER_CLASS));

            for (int i = 0; i < poolSize; i++) {
                connectionList.add(DriverManager.getConnection(DBUrl, DBlogin, DBpassword));
            }
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static class PoolHolder {
        public static final DBConnectionPool HOLDER_INSTANCE = new DBConnectionPool();
    }

    public static DBConnectionPool getInstance() {
        return PoolHolder.HOLDER_INSTANCE;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionList.poll(15, TimeUnit.SECONDS);
            if (connection == null) {
                connection = DriverManager.getConnection(DBUrl, DBlogin, DBpassword);
            }
        } catch (InterruptedException | SQLException ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
    }
}
