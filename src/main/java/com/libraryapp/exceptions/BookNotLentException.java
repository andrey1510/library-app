package com.libraryapp.exceptions;

public class BookNotLentException extends RuntimeException {
    public BookNotLentException(String message) {
        super(message);
    }
}
