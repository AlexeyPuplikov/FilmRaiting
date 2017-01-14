package by.epam.filmrating.entity;

public class User extends Entity{

    private String login;
    private String password;
    private EnumStatus status;
    private EnumRole role;

    public User() {
        super();
    }

    public User(int userId, String login, String password, EnumStatus status, EnumRole role) {
        super(userId);
        this.login = login;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public int getUserId() {
        return super.getId();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public EnumStatus getStatus() {
        return status;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setUserId(int userId) {
        super.setId(userId);
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(EnumStatus status) {
        this.status = status;
    }

    public void setRole(EnumRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        if (status != user.status) return false;
        return role == user.role;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", role=" + role +
                "} " + super.toString();
    }
}
