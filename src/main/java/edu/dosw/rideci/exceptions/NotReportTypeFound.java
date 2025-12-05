package edu.dosw.rideci.exceptions;

public class NotReportTypeFound extends RuntimeException {
    public NotReportTypeFound(int year) {
        super("The user provided does not exist " + year);
    }

    public NotReportTypeFound(String message) {
        super(message);
    }
}