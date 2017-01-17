package by.epam.filmrating.entity;

import java.util.Date;

public class Actor extends Entity {
    private String name;
    private Date dateOfBirth;
    private String info;

    public Actor() {
        super();
    }

    public Actor(int actorId, String name, Date dateOfBirth, String info) {
        super(actorId);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
    }

    public int getActorId() {
        return super.getId();
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public void setActorId(int actorId) {
        super.setId(actorId);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Actor actor = (Actor) o;

        return name.equals(actor.name) && dateOfBirth.equals(actor.dateOfBirth) && info.equals(actor.info);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + info.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", info='" + info + '\'' +
                "} " + super.toString();
    }
}
