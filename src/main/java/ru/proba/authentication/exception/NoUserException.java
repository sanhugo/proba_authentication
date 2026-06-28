package ru.proba.authentication.exception;

public class NoUserException extends RuntimeException {
    public NoUserException(String message) {
        super(message);
    }
}
