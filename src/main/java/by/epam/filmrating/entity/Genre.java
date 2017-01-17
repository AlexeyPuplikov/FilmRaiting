package by.epam.filmrating.entity;

public class Genre extends Entity {

    private EnumGenre name;

    public Genre() {
        super();
    }

    public Genre(int genreId, EnumGenre name) {
        super(genreId);
        this.name = name;
    }

    public int getGenreId() {
        return super.getId();
    }

    public String getName() {
        return null;
    }

    public void setGenreId(int genreId) {
        super.setId(genreId);
    }

    public void setName(EnumGenre name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Genre genre = (Genre) o;

        return name == genre.name;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name=" + name +
                "} " + super.toString();
    }
}
