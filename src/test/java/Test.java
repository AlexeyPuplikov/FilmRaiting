import by.epam.filmrating.dao.*;
import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.ConnectionPoolException;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        FilmDAO dao = new FilmDAO();
        try {
            //List<Status> statuses = dao.findAll();
            //Status status = statuses.get(1);
            //status.setStatusId(8);
            //System.out.print(statuses);
            //dao.create(status);
            System.out.println(dao.findEntityById(2));
            //dao.delete(8);
            System.out.println(dao.findAll());
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
