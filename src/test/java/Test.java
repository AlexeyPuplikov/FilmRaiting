import by.epam.filmrating.connection.DBConnectionPool;
import by.epam.filmrating.dao.ActorDAO;
import by.epam.filmrating.dao.FilmDAO;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Test {
    /*private final static String SQL_INSERT = "INSERT INTO actor(ACTOR_ID, NAME, DATE_OF_BIRTH, INFO) VALUES(?,?,?,?)";
    private static Connection connection;
    public static void main(String[] args) {
        DBConnectionPool dbConnectionPool = DBConnectionPool.getInstance();
        connection = dbConnectionPool.getConnection();
        Actor actor = new Actor(5, "John", "1994", "List");
        System.out.print(insertActor(getPreparedStatement(), actor));
    }
    private static PreparedStatement getPreparedStatement() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    private static boolean insertActor(PreparedStatement preparedStatement, Actor actor) {
        boolean flag = false;
        try {
            preparedStatement.setInt(1, actor.getActorId());
            preparedStatement.setString(2, actor.getName());
            preparedStatement.setString(3, actor.getDateOfBirth());
            preparedStatement.setString(4, actor.getInfo());
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }*/
    public static void main(String[] args) {
        ActorDAO dao = new ActorDAO();
        try {
            List<Actor> actors = dao.findAll();

            actors.get(0).setActorId(4);
            Actor actor = actors.get(0);
            System.out.print(dao.create(actor));
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
