package by.epam.filmrating.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Film extends Entity {
    private String name;
    private Date year;
    private String country;
    private String description;
    private Date premiere;
    private int time;
    private List<StageDirector> stageDirectors = new ArrayList<StageDirector>();
    private List<Genre> genres = new ArrayList<Genre>();
    private List<Actor> actors = new ArrayList<Actor>();
    private List<Comment> comments = new ArrayList<Comment>();

    public Film() {
        super();
    }

    public Film(int filmId, String name, Date year, String country, String description, Date premiere, int time, List<StageDirector> stageDirectors, List<Genre> genres, List<Actor> actors, List<Comment> comments) {
        super(filmId);
        this.name = name;
        this.year = year;
        this.country = country;
        this.description = description;
        this.premiere = premiere;
        this.time = time;
        this.stageDirectors = stageDirectors;
        this.genres = genres;
        this.actors = actors;
        this.comments = comments;
    }

    public int getFilmId() {
        return super.getId();
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

    public List<StageDirector> getStageDirectors() {
        return stageDirectors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setFilmId(int filmId) {
        super.setId(filmId);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPremiere(Date premiere) {
        this.premiere = premiere;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setStageDirectors(ArrayList<StageDirector> stageDirectors) {
        this.stageDirectors = stageDirectors;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Film film = (Film) o;
        return time == film.time && name.equals(film.name) && year.equals(film.year) && country.equals(film.country) && description.equals(film.description) && premiere.equals(film.premiere) && stageDirectors.equals(film.stageDirectors) && genres.equals(film.genres) && actors.equals(film.actors) && comments.equals(film.comments);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
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
                "name='" + name + '\'' +
                ", year=" + year +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", premiere=" + premiere +
                ", time=" + time +
                ", stageDirectors=" + stageDirectors +
                ", genres=" + genres +
                ", actors=" + actors +
                ", comments=" + comments +
                "} " + super.toString();
    }
}
