package by.epam.filmrating.entity;

public class Genre extends Entity{
    private int genreId;
    private EnumGenre name;

    public Genre(int genreId, EnumGenre name) {
        this.genreId = genreId;
        this.name = name;
    }

    public int getGenreId() {
        return genreId;
    }

    public EnumGenre getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (genreId != genre.genreId) return false;
        return name == genre.name;

    }

    @Override
    public int hashCode() {
        int result = genreId;
        result = 31 * result + name.hashCode();
        return result;
    }
}
