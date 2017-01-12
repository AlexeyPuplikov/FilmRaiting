package by.epam.filmrating.entity;

import java.util.Date;

public class Actor {
    private int actorId;
    private String name;
    private String dateOfBirth;
    private String info;

    public Actor(int actorId, String name, String dateOfBirth, String info) {
        this.actorId = actorId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
    }

    public int getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getInfo() {
        return info;
    }
}
