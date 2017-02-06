package by.epam.filmrating.entity;

import java.util.Objects;

public class User extends Entity {
    private String login;
    private String password;
    private String status;
    private EnumRole role;

    public User() {
        super();
        this.setStatus("NEW");
        this.setRole(EnumRole.USER);
    }

    public User(int userId, String login, String password, String status, String role) {
        super(userId);
        this.login = login;
        this.password = password;
        this.status = EnumStatus.valueOf(status).getName();
        this.role = EnumRole.valueOf(role);
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

    public String getStatus() {
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

    public void setStatus(String status) {
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
        return login.equals(user.login) && password.equals(user.password) && Objects.equals(status, user.status) && role == user.role;
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
