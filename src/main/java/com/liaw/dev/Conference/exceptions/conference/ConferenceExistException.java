package com.liaw.dev.Conference.exceptions.conference;

public class ConferenceExistException extends RuntimeException {
    public ConferenceExistException(String message) {
        super(message);
    }
}
