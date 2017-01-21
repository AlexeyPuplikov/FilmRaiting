package by.epam.filmrating.entity;

import java.util.Date;

public class StageDirector extends Entity {
    private String name;
    private Date dateOfBirth;
    private String info;

    public StageDirector() {
        super();
    }

    public StageDirector(int stageDirectorId, String name, Date dateOfBirth, String info) {
        super(stageDirectorId);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
    }

    public int getStageDirectorId() {
        return super.getId();
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

    public void setStageDirectorId(int stageDirectorId) {
        super.setId(stageDirectorId);
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
        StageDirector that = (StageDirector) o;
        return name.equals(that.name) && dateOfBirth.equals(that.dateOfBirth) && info.equals(that.info);
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
        return "StageDirector{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", info='" + info + '\'' +
                "} " + super.toString();
    }
}
