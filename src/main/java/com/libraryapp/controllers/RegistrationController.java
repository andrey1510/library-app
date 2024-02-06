package com.libraryapp.controllers;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.models.Book;
import com.libraryapp.models.Client;
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
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
        registrationService.createBook(bookDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("register_client")
    @Operation(description = "Зарегистрировать клиента в библиотеке.")
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
        registrationService.createClient(clientDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
