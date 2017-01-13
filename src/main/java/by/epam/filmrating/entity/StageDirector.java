package by.epam.filmrating.entity;

import java.util.Date;

public class StageDirector extends Entity{
    private int stageDirectorId;
    private String name;
    private Date dateOfBirth;
    private String info;

    public StageDirector(int stageDirectorId, String name, Date dateOfBirth, String info) {
        this.stageDirectorId = stageDirectorId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
    }

    public int getStageDirectorId() {
        return stageDirectorId;
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

        StageDirector that = (StageDirector) o;

        if (stageDirectorId != that.stageDirectorId) return false;
        if (!name.equals(that.name)) return false;
        if (!dateOfBirth.equals(that.dateOfBirth)) return false;
        return info.equals(that.info);

    }

    @Override
    public int hashCode() {
        int result = stageDirectorId;
        result = 31 * result + name.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + info.hashCode();
        return result;
    }
}
