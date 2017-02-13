package by.epam.filmrating.util;

public class AddUtil {
    private final static int CURRENT_YEAR = 2017;
    private final static int START_YEAR = 1900;
    private final static int TIME_START = 15;
    private final static int TIME_END = 400;
    private final static int BUDGET_START = 2000000;
    private final static int BUDGET_END = 500000000;

    public boolean checkName(String name) {
        return !name.isEmpty();
    }

    public boolean checkYear(int year) {
        return year <= CURRENT_YEAR && year > START_YEAR;
    }

    public boolean checkDescription(String description) {
        return !description.isEmpty();
    }

    public boolean checkTime(int time) {
        return time <= TIME_END && time >= TIME_START;
    }

    public boolean checkBudget(int budget) {
        return budget <= BUDGET_END && budget >= BUDGET_START;
    }
}
