package by.epam.filmrating.entity;

public class Status extends Entity {
    private EnumStatus name;

    public Status() {
        super();
    }

    public Status(int statusId, EnumStatus name) {
        super(statusId);
        this.name = name;
    }

    public EnumStatus getName() {
        return name;
    }

    public void setName(EnumStatus name) {
        this.name = name;
    }

    public void setStatusId(int statusId) {
        super.setId(statusId);
    }

    public int getStatusId() {
        return super.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Status status = (Status) o;
        return name == status.name;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Status{" +
                "name=" + name +
                "} " + super.toString();
    }
}
