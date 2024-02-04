package com.libraryapp.exceptions;

public class BookNotLentToClientException extends RuntimeException {
    public BookNotLentToClientException(String message) {
        super(message);
    }
}
