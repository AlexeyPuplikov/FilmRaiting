package by.epam.filmrating.entity;

import java.util.Date;

public class Actor extends Entity{
    private int actorId;
    private String name;
    private Date dateOfBirth;
    private String info;

    public Actor(int actorId, String name, Date dateOfBirth, String info) {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if (actorId != actor.actorId) return false;
        if (!name.equals(actor.name)) return false;
        if (!dateOfBirth.equals(actor.dateOfBirth)) return false;
        return info.equals(actor.info);

    }

    @Override
    public int hashCode() {
        int result = actorId;
        result = 31 * result + name.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + info.hashCode();
        return result;
    }
}
