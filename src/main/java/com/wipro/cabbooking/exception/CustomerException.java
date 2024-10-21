package com.wipro.cabbooking.exception;

public class CustomerException extends RuntimeException {
    public CustomerException() {
        super(); // Calls the superclass constructor
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerException(Throwable cause) {
        super(cause);
    }
}
