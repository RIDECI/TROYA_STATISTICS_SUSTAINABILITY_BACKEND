package edu.dosw.rideci.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int year) {
        super("The user provided does not exist " + year);
    }

    public UserNotFoundException(String message) {
        super(message);
    }}
