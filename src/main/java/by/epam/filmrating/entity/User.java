package by.epam.filmrating.entity;

public class User {
    private int userId;
    private String login;
    private String password;
    private EnumStatus status;
    private EnumRole role;

    public User(int userId, String login, String password, EnumStatus status, EnumRole role) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public int getUserId() {
        return userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        if (status != user.status) return false;
        return role == user.role;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
