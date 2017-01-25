package by.epam.filmrating.exception;

public class DAOException extends Exception {
    public DAOException() {
        super();
    }

    public DAOException(String message, Throwable exception) {
        super(message, exception);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable exception) {
        super(exception);
    }
}
