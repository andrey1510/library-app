package com.libraryapp.controllers;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.Client;
import com.libraryapp.services.LendingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lending/")
@RequiredArgsConstructor
public class LendingController {

    private static final String BOOK_NOT_LENT = "В настоящее время эта книга не выдана какому-либо клиенту.";
    private static final String MAXIMUM_COPIES_LENT = "Превышено максимальное количество выданных копий книги.";
    private static final String NO_BOOKS_IN_LIBRARY = "В библиотеке не зарегистрировано ни одной книги.";

    private final LendingService lendingService;

    @GetMapping("books")
    @Operation(description = "Показать все книги, которые есть в библиотеке.")
    public List<Book> getAllBooks() {

        //ToDO exception если лист пустой

        List<Book> books = lendingService.getAllBooks();

        return books;
    }

    @GetMapping("clients/{isbn}")
    @Operation(description = "Показать клиентов, которым была выдана книга с определенным ISBN.")
    public List<Client> getClientsByBookIsbn(@PathVariable("isbn") String isbn){

        //ToDO exception если таких клиентов нет

        List<Client> clients = lendingService.getClientsByBookIsbn(isbn);

        return clients;
    }

}
