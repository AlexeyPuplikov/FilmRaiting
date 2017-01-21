package by.epam.filmrating.entity;

public enum EnumStatus {
    NEW("Новичок"),
    AMATURE("Любитель"),
    EXPERIENCED("Опытный"),
    CRITIC("Критик");

    private final String name;

    EnumStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
