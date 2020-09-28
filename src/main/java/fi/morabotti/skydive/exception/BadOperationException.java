package fi.morabotti.skydive.exception;

public class BadOperationException extends ApplicationException {
    public BadOperationException() {
    }

    public BadOperationException(String message) {
        super(message);
    }

    public BadOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadOperationException(Throwable cause) {
        super(cause);
    }

    public BadOperationException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
