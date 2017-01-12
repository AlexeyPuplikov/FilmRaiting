package by.epam.filmrating.entity;

import java.util.ArrayList;
import java.util.Date;

public class Film {
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
}
