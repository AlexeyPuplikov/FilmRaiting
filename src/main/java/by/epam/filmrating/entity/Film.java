package by.epam.filmrating.entity;

import java.util.ArrayList;
import java.util.Date;

public class Film extends Entity{
    private int filmId;
    private String name;
    private Date year;
    private String country;
    private String description;
    private Date premiere;
    private int time;
    private ArrayList<StageDirector> stageDirectors = new ArrayList<StageDirector>();
    private ArrayList<Genre> genres = new ArrayList<Genre>();
    private ArrayList<Actor> actors = new ArrayList<Actor>();
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    public Film(int filmId, String name, Date year, String country, String description, Date premiere, int time) {
        this.filmId = filmId;
        this.name = name;
        this.year = year;
        this.country = country;
        this.description = description;
        this.premiere = premiere;
        this.time = time;
    }


    public int getFilmId() {
        return filmId;
    }

    public String getName() {
        return name;
    }

    public Date getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public Date getPremiere() {
        return premiere;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<StageDirector> getStageDirectors() {
        return stageDirectors;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (filmId != film.filmId) return false;
        if (time != film.time) return false;
        if (!name.equals(film.name)) return false;
        if (!year.equals(film.year)) return false;
        if (!country.equals(film.country)) return false;
        if (!description.equals(film.description)) return false;
        if (!premiere.equals(film.premiere)) return false;
        if (!stageDirectors.equals(film.stageDirectors)) return false;
        if (!genres.equals(film.genres)) return false;
        if (!actors.equals(film.actors)) return false;
        return comments.equals(film.comments);

    }

    @Override
    public int hashCode() {
        int result = filmId;
        result = 31 * result + name.hashCode();
        result = 31 * result + year.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + premiere.hashCode();
        result = 31 * result + time;
        result = 31 * result + stageDirectors.hashCode();
        result = 31 * result + genres.hashCode();
        result = 31 * result + actors.hashCode();
        result = 31 * result + comments.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", premiere=" + premiere +
                ", time=" + time +
                ", stageDirectors=" + stageDirectors +
                ", genres=" + genres +
                ", actors=" + actors +
                ", comments=" + comments +
                '}';
    }
}
