package com.libraryapp.exceptions;

public class BookAlreadyLentToClientException extends RuntimeException {
    public BookAlreadyLentToClientException(String message) {
        super(message);
    }
}
