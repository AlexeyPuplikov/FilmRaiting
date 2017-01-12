package by.epam.filmrating.entity;

public class Rating {
    private int filmId;
    private int userId;
    private int mark;

    public Rating(int filmId, int userId, int mark) {
        this.filmId = filmId;
        this.userId = userId;
        this.mark = mark;
    }

    public int getFilmId() {
        return filmId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMark() {
        return mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (filmId != rating.filmId) return false;
        if (userId != rating.userId) return false;
        return mark == rating.mark;

    }

    @Override
    public int hashCode() {
        int result = filmId;
        result = 31 * result + userId;
        result = 31 * result + mark;
        return result;
    }
}
