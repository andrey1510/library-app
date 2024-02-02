package com.libraryapp.exceptions;

public class MaximumCopiesLentException extends RuntimeException {
    public MaximumCopiesLentException(String message) {
        super(message);
    }
}
