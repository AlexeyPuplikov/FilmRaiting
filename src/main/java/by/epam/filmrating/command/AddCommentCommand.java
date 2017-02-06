package by.epam.filmrating.command;

import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCommentCommand implements ActionCommand {
    private CommentService commentService;

    public AddCommentCommand() {
        this.commentService = new CommentService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String userID = request.getParameter("userId");
        String filmId = request.getParameter("filmId");
        String text = request.getParameter("text");
        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(userID));
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
