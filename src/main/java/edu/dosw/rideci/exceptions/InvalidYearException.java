package edu.dosw.rideci.exceptions;

public class InvalidYearException extends RuntimeException {

    public InvalidYearException(int year) {
        super("The year provided is invalid: " + year);
    }

    public InvalidYearException(String message) {
        super(message);
    }
}