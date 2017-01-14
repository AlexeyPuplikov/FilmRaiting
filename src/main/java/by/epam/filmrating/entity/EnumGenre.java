package by.epam.filmrating.entity;

public enum EnumGenre {
    THRILLER("Триллер"),
    HORROR("Ужасы"),
    DRAMA("Драма"),
    MELODRAMA("Мелодрама"),
    WESTERN("Вестерн"),
    COMEDY("Комедия"),
    ACTION("Боевик"),
    SCIENCE_FICTION("Научная фантастика"),
    FANTASY("Фантастика"),
    MUSICAL("Мюзикл");

    private final String name;

    EnumGenre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
