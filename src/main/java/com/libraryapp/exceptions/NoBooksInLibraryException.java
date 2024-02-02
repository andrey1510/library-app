package com.libraryapp.exceptions;

public class NoBooksInLibraryException extends RuntimeException {
    public NoBooksInLibraryException(String message) {
        super(message);
    }
}
