package com.libraryapp.controllers;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.LendingRecord;
import com.libraryapp.entities.Client;
import com.libraryapp.exceptions.BookAlreadyLentToClientException;
import com.libraryapp.exceptions.BookNotFoundException;
import com.libraryapp.exceptions.BookNotLentToAnyoneException;
import com.libraryapp.exceptions.BookNotLentToClientException;
import com.libraryapp.exceptions.ClientNotFoundException;
import com.libraryapp.exceptions.NoBooksInLibraryException;
import com.libraryapp.services.LendingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lending/")
@RequiredArgsConstructor
public class LendingController {

    private static final String BOOK_NOT_LENT_TO_ANYONE = "В настоящее время книга с таким ISBN не выдана какому-либо клиенту.";
    private static final String BOOK_NOT_LENT_TO_CLIENT = "Книга с таким ISBN не выдана этому клиенту.";
    private static final String BOOK_ALREADY_LENT_CLIENT = "Книга с таким ISBN уже выдана этому клиенту.";
    private static final String MAXIMUM_COPIES_LENT = "Превышено максимальное количество выданных копий книги.";
    private static final String NO_BOOKS_IN_LIBRARY = "В библиотеке не зарегистрировано ни одной книги.";
    private static final String BOOK_NOT_FOUND = "В библиотеке не зарегистрирована книга с таким ISBN.";
    private static final String CLIENT_NOT_FOUND = "В библиотеке не зарегистрирован клиент с таким читательским билетом.";


    private final LendingService lendingService;

    @GetMapping("books")
    @Operation(description = "Показать все книги, которые есть в библиотеке.")
    public List<Book> getAllBooks() {

        List<Book> books = lendingService.getAllBooks();

        if(books.isEmpty()) {
            throw new NoBooksInLibraryException(NO_BOOKS_IN_LIBRARY);
        }

        return books;
    }

    @GetMapping("clients/{isbn}")
    @Operation(description = "Показать клиентов, которым была выдана книга с определенным ISBN.")
    public List<Client> getClientsByBookIsbn(@PathVariable("isbn") String isbn){

        List<Client> clients = lendingService.getClientsByBookIsbn(isbn);

        if (clients.isEmpty()) {
            throw new BookNotLentToAnyoneException(BOOK_NOT_LENT_TO_ANYONE);
        }

        return clients;
    }

    @PostMapping("lend-book/{isbn}&{library-card}")
    @Operation(description = "Выдать книгу клиенту.")
    public ResponseEntity<String> lendBook(
            @PathVariable("isbn") String isbn,
            @PathVariable("library-card") String libraryCard) {

        //ToDO поиск по LendingRecord с таким ISBN, exception если превышен максимум копий

        Book book = lendingService.getBookByISBN(isbn)
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND));
        Client client = lendingService.getClientByLibraryCard(libraryCard)
                .orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND));

        if (lendingService.getLendingRecordByIsbnAndLibraryCard(isbn, libraryCard).isPresent()) {
            throw new BookAlreadyLentToClientException(BOOK_ALREADY_LENT_CLIENT);
        }

        LendingRecord lendingRecord = new LendingRecord(book, client);

        lendingService.createBookLending(lendingRecord);
        return new ResponseEntity<>(lendingRecord.getBook().getIsbn(), HttpStatus.OK);

    }

    @DeleteMapping("return-book/{isbn}&{library-card}")
    public ResponseEntity<String> returnBook(
            @PathVariable("isbn") String isbn,
            @PathVariable("library-card") String libraryCard) {

        if (lendingService.getBookByISBN(isbn).isEmpty()) {
            throw new BookNotFoundException(BOOK_NOT_FOUND);
        }
        if (lendingService.getClientByLibraryCard(libraryCard).isEmpty()) {
            throw new ClientNotFoundException(CLIENT_NOT_FOUND);
        }

        LendingRecord lendingRecord = lendingService.getLendingRecordByIsbnAndLibraryCard(isbn, libraryCard)
                .orElseThrow(() -> new BookNotLentToClientException(BOOK_NOT_LENT_TO_CLIENT));

        lendingService.deleteById(lendingRecord.getId());

        return new ResponseEntity<>(lendingRecord.getBook().getIsbn(), HttpStatus.OK);

    }

}
