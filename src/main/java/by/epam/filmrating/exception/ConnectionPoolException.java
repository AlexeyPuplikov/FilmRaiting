package by.epam.filmrating.exception;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message, Throwable exception) {
        super(message, exception);
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Throwable exception) {
        super(exception);
    }
}
