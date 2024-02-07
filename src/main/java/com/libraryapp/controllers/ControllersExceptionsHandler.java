package com.libraryapp.controllers;

import com.libraryapp.exceptions.BookAlreadyLentToClientException;
import com.libraryapp.exceptions.BookNotFoundException;
import com.libraryapp.exceptions.BookNotLentToAnyoneException;
import com.libraryapp.exceptions.BookNotLentToClientException;
import com.libraryapp.exceptions.ClientNotFoundException;
import com.libraryapp.exceptions.NegativeLendingTermException;
import com.libraryapp.exceptions.NoCopiesLeftException;
import com.libraryapp.exceptions.NoBooksInLibraryException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllersExceptionsHandler {

    @ExceptionHandler(BookNotLentToAnyoneException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleBookNotLentException(BookNotLentToAnyoneException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NoCopiesLeftException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleNoCopiesLeftException(NoCopiesLeftException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NoBooksInLibraryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNoBooksInLibraryException(NoBooksInLibraryException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleBookNotFoundException(BookNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleClientNotFoundException(ClientNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(BookAlreadyLentToClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBookAlreadyLentToClientException(BookAlreadyLentToClientException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(BookNotLentToClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBookNotLentToClientException(BookNotLentToClientException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NegativeLendingTermException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleNegativeLendingTermException(NegativeLendingTermException ex) {
        return new ErrorResponse(ex.getMessage());
    }


    @AllArgsConstructor
    @Getter
    private static class ErrorResponse {
        @Schema(description = "Сообщение об ошибке.", example = "Описание ошибки.")
        private final String message;
    }

}
