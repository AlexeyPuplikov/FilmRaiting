package by.epam.filmrating.entity;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Film extends Entity {
    private StageDirector stageDirector;
    private String name;
    private int year;
    private String description;
    private Date premiere;
    private int time;
    private int budget;
    private List<Country> countries = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private List<Actor> actors = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private Blob cover;

    public Film() {
        super();
    }

    public Film(int filmId, StageDirector stageDirector, String name, int year, String description, Date premiere, int time, int budget, List<Country> countries, List<Genre> genres, List<Actor> actors, List<Comment> comments) {
        super(filmId);
        this.stageDirector = stageDirector;
        this.name = name;
        this.year = year;
        this.description = description;
        this.premiere = premiere;
        this.time = time;
        this.budget = budget;
        this.countries = countries;
        this.genres = genres;
        this.actors = actors;
        this.comments = comments;
    }

    public int getFilmId() {
        return super.getId();
    }

    public StageDirector getStageDirector() {
        return stageDirector;
    }

    public void setStageDirector(StageDirector stageDirector) {
        this.stageDirector = stageDirector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPremiere() {
        return premiere;
    }

    public void setPremiere(Date premiere) {
        this.premiere = premiere;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setFilmId(int filmId) {
        super.setId(filmId);
    }

    public Blob getCover() {
        return cover;
    }

    public void setCover(Blob cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Film film = (Film) o;

        return year == film.year && time == film.time && budget == film.budget && stageDirector.equals(film.stageDirector) && name.equals(film.name) && description.equals(film.description) && premiere.equals(film.premiere) && countries.equals(film.countries) && genres.equals(film.genres) && actors.equals(film.actors) && comments.equals(film.comments) && cover.equals(film.cover);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + stageDirector.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + year;
        result = 31 * result + description.hashCode();
        result = 31 * result + premiere.hashCode();
        result = 31 * result + time;
        result = 31 * result + budget;
        result = 31 * result + countries.hashCode();
        result = 31 * result + genres.hashCode();
        result = 31 * result + actors.hashCode();
        result = 31 * result + comments.hashCode();
        result = 31 * result + cover.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "stageDirector=" + stageDirector +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", premiere=" + premiere +
                ", time=" + time +
                ", budget=" + budget +
                ", countries=" + countries +
                ", genres=" + genres +
                ", actors=" + actors +
                ", comments=" + comments +
                ", cover=" + cover +
                "} " + super.toString();
    }
}
