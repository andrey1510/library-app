package com.libraryapp.controllers;

import com.libraryapp.exceptions.BookNotLentException;
import com.libraryapp.exceptions.MaximumCopiesLentException;
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

    @ExceptionHandler(BookNotLentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleBookNotLentException(BookNotLentException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(MaximumCopiesLentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMaximumCopiesLentException(MaximumCopiesLentException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NoBooksInLibraryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNoBooksInLibraryException(NoBooksInLibraryException ex) {
        return new ErrorResponse(ex.getMessage());
    }



    @AllArgsConstructor
    @Getter
    private static class ErrorResponse {
        @Schema(description = "Сообщение об ошибке.", example = "В настоящее время эта книга не выдана какому-либо клиенту.")
        private final String message;
    }

}
