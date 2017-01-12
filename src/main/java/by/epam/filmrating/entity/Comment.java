package by.epam.filmrating.entity;

public class Comment {
    private int commentId;
    private String text;
    private int filmId;
    private int userId;

    public Comment(int commentId, String text, int filmId, int userId) {
        this.commentId = commentId;
        this.text = text;
        this.filmId = filmId;
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (commentId != comment.commentId) return false;
        if (filmId != comment.filmId) return false;
        if (userId != comment.userId) return false;
        return text.equals(comment.text);

    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + text.hashCode();
        result = 31 * result + filmId;
        result = 31 * result + userId;
        return result;
    }
}
