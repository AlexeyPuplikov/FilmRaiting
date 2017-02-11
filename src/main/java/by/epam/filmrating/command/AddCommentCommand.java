package by.epam.filmrating.command;

import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class AddCommentCommand implements ActionCommand {
    private CommentService commentService;

    public AddCommentCommand() {
        this.commentService = new CommentService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String filmId = request.getParameter("filmId");
        String text = request.getParameter("text");
        Comment comment = new Comment();
        comment.setUserId(user.getUserId());
        comment.setFilmId(Integer.parseInt(filmId));
        comment.setText(text);
        comment.setCreationDate(new Date());
        try {
            commentService.create(comment);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return "redirect:/controller?command=VIEW_FILM&filmId=" + filmId;
    }
}
