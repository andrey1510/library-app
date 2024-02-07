package com.libraryapp.controllers;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.dto.LendingRecordDTO;
import com.libraryapp.exceptions.NegativeLendingTermException;
import com.libraryapp.exceptions.NoCopiesLeftException;
import com.libraryapp.messages.ReturnBookResponse;
import com.libraryapp.models.Book;
import com.libraryapp.models.LendingRecord;
import com.libraryapp.models.Client;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lending/")
@RequiredArgsConstructor
public class LendingController {

    private static final String BOOK_NOT_LENT_TO_ANYONE = "В настоящее время книга с таким ISBN не выдана какому-либо клиенту.";
    private static final String BOOK_NOT_LENT_TO_CLIENT = "Книга с таким ISBN не выдана этому клиенту.";
    private static final String BOOK_ALREADY_LENT_TO_CLIENT = "Книга с таким ISBN уже выдана этому клиенту.";
    private static final String NO_COPIES_LEFT = "Превышено максимальное количество выданных копий книги.";
    private static final String NO_BOOKS_IN_LIBRARY = "В библиотеке не зарегистрировано ни одной книги.";
    private static final String BOOK_NOT_FOUND = "В библиотеке не зарегистрирована книга с таким ISBN.";
    private static final String CLIENT_NOT_FOUND = "В библиотеке не зарегистрирован клиент с таким читательским билетом.";
    private static final String BOOK_SUCCESSFULLY_RETURNED = "Книга успешно возвращена.";
    private static final String NEGATIVE_LENDING_TERM = "Срок выдачи книги не может быть отрицательным.";

    private final LendingService lendingService;


    @GetMapping("books")
    @Operation(description = "Показать все книги, которые есть в библиотеке.")
    public List<BookDTO> getAllBooks() {

        List<BookDTO> books = lendingService.getAllBooks();

        if(books.isEmpty()) {
            throw new NoBooksInLibraryException(NO_BOOKS_IN_LIBRARY);
        }

        return books;
    }


    @GetMapping("clients/{isbn}")
    @Operation(description = "Показать клиентов, которым была выдана книга с определенным ISBN.")
    public List<ClientDTO> getClientsByBookIsbn(@PathVariable("isbn") String isbn){

        if (lendingService.getBookByISBN(isbn).isEmpty()) {
            throw new BookNotFoundException(BOOK_NOT_FOUND);
        }

        List<ClientDTO> clients = lendingService.getClientsByIsbn(isbn);

        if (clients.isEmpty()) {
            throw new BookNotLentToAnyoneException(BOOK_NOT_LENT_TO_ANYONE);
        }

        return clients;
    }


    @PostMapping("lend-book")
    @Operation(description = "Выдать книгу клиенту.")
    public ResponseEntity<LendingRecordDTO> lendBook(@RequestParam String isbn,
                                                     @RequestParam String libraryCard,
                                                     @RequestParam Integer lendingTerm) {

        if(lendingTerm < 0 ) {
            throw new NegativeLendingTermException(NEGATIVE_LENDING_TERM);
        }

        Book book = lendingService.getBookByISBN(isbn)
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND));
        Client client = lendingService.getClientByLibraryCard(libraryCard)
                .orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND));

        if (lendingService.getLendingRecordByIsbnAndLibraryCard(isbn, libraryCard).isPresent()) {
            throw new BookAlreadyLentToClientException(BOOK_ALREADY_LENT_TO_CLIENT);
        }

        if(book.getMaxCopies() - book.getLentCopies() <= 0) {
            throw new NoCopiesLeftException(NO_COPIES_LEFT);
        }

        LendingRecordDTO lendingRecordDTO = lendingService.createLendingRecord(book, client, lendingTerm);
        lendingService.updateLentCopies(book.getLentCopies() + 1, book.getIsbn());

        return new ResponseEntity<>(lendingRecordDTO, HttpStatus.OK);
    }


    @DeleteMapping("return-book")
    public ResponseEntity<ReturnBookResponse> returnBook(@RequestParam String isbn,
                                                         @RequestParam String libraryCard) {

        if (lendingService.getClientByLibraryCard(libraryCard).isEmpty()) {
            throw new ClientNotFoundException(CLIENT_NOT_FOUND);
        }

        Book book = lendingService.getBookByISBN(isbn)
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND));

        LendingRecord lendingRecord = lendingService.getLendingRecordByIsbnAndLibraryCard(isbn, libraryCard)
                .orElseThrow(() -> new BookNotLentToClientException(BOOK_NOT_LENT_TO_CLIENT));

        lendingService.deleteById(lendingRecord.getId());
        lendingService.updateLentCopies(book.getLentCopies() - 1, book.getIsbn());

        return new ResponseEntity<>(new ReturnBookResponse(BOOK_SUCCESSFULLY_RETURNED), HttpStatus.OK);
    }

}
