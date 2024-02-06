package com.libraryapp.exceptions;

public class NoCopiesLeftException extends RuntimeException {
    public NoCopiesLeftException(String message) {
        super(message);
    }
}
