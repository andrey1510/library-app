package com.libraryapp.exceptions;

public class BookNotLentToAnyoneException extends RuntimeException {
    public BookNotLentToAnyoneException(String message) {
        super(message);
    }
}
