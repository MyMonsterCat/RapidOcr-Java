package io.github.mymonstercat.exception;

public class LoadException extends Exception {

    public LoadException() {
        super();
    }

    public LoadException(String message) {
        super(message);
    }

    public LoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadException(Throwable cause) {
        super(cause);
    }
}
