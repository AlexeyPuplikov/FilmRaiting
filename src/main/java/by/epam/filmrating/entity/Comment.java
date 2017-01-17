package by.epam.filmrating.entity;

import java.util.Date;

public class Comment extends Entity {
    private String text;
    private int filmId;
    private int userId;

    public Comment() {
        super();
    }

    public Comment(int commentId, String text, int filmId, int userId) {
        super(commentId);
        this.text = text;
        this.filmId = filmId;
        this.userId = userId;
    }

    public int getCommentId() {
        return super.getId();
    }

    public String getText() {
        return text;
    }

    public int getFilmId() {
        return filmId;
    }

    public int getUserId() {
        return userId;
    }

    public void setCommentId(int commentId) {
        super.setId(commentId);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        return filmId == comment.filmId && userId == comment.userId && text.equals(comment.text);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + filmId;
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", filmId=" + filmId +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
