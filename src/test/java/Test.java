import by.epam.filmrating.entity.Rating;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.*;

public class Test {
    public static void main(String[] args) {
        FilmService service = new FilmService();
        Rating rating = new Rating();
        rating.setUserId(4);
        rating.setFilmId(1);
        rating.setMark(2);
        try {
            System.out.println(service.setFilmRating(rating));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
