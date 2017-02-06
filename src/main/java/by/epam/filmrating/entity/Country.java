package by.epam.filmrating.entity;

public class Country extends Entity {
    private String name;

    public Country() {
        super();
    }

    public Country(int countryId, String name) {
        super(countryId);
        this.name = name;
    }

    public int getCountryId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setCountryId(int countryId) {
        super.setId(countryId);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Country country = (Country) o;

        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
