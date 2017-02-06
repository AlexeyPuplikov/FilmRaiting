package by.epam.filmrating.entity;

public class Rating extends Entity {
    private int userId;
    private int filmId;
    private int mark;

    public Rating() {
        super();
    }

    public Rating(int ratingId, int userId, int filmId, int mark) {
        super(ratingId);
        this.userId = userId;
        this.filmId = filmId;
        this.mark = mark;
    }

    public int getRatingId() {
        return super.getId();
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

    public void setRatingId(int ratingId) {
        super.setId(ratingId);
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rating rating = (Rating) o;
        return filmId == rating.filmId && userId == rating.userId && mark == rating.mark;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + filmId;
        result = 31 * result + userId;
        result = 31 * result + mark;
        return result;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "filmId=" + filmId +
                ", userId=" + userId +
                ", mark=" + mark +
                "} " + super.toString();
    }
}
