package test.by.epam.filmrating;

import by.epam.filmrating.command.SetRatingCommand;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActionTest {
    private SetRatingCommand setRatingCommand;
    private User user;

    @Before
    public void SelectUser() throws ServiceException {
        setRatingCommand = new SetRatingCommand();
        user = new User();
        user.setStatus("NEW");
    }

    @Test
    public void ChangeStatus() throws ServiceException {
        boolean check = setRatingCommand.updateStatus(8, 5, user);
        Assert.assertFalse(check);
    }

    @Test
    public void ChangeStatus1() throws ServiceException {
        boolean check = setRatingCommand.updateStatus(2, 5, user);
        Assert.assertTrue(check);
    }

}
