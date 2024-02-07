package com.libraryapp.exceptions;

public class NegativeLendingTermException extends RuntimeException {
    public NegativeLendingTermException(String message) {
        super(message);
    }
}
