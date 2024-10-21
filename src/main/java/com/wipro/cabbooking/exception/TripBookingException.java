package com.wipro.cabbooking.exception;

public class TripBookingException extends RuntimeException {
    public TripBookingException() {
        super(); // Calls the superclass constructor
    }

    public TripBookingException(String message) {
        super(message);
    }

    public TripBookingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TripBookingException(Throwable cause) {
        super(cause);
    }
}
