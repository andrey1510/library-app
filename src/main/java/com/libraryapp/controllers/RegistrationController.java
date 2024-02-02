package com.libraryapp.controllers;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.Client;
import com.libraryapp.services.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration/")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("register_book")
    @Operation(description = "Зарегистрировать книгу в библиотеке.")
    public ResponseEntity<Book> registerBook(@RequestBody Book book) {

        registrationService.registerBook(book);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("register_client")
    @Operation(description = "Зарегистрировать клиента в библиотеке.")
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {

        registrationService.registerClient(client);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
