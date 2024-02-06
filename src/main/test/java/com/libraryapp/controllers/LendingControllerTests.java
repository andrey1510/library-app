package com.libraryapp.controllers;

import com.libraryapp.services.LendingService;
import com.libraryapp.testdata.TestData;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LendingController.class)
class LendingControllerTests extends TestData {

    @MockBean
    LendingService lendingService;

    @Autowired
    private MockMvc mockMvc;


    ///// getAllBooks() method tests /////

    @Test
    @SneakyThrows
    void getAllBooksTest() {
        String isbn = "1 isbn";

        when(lendingService.getAllBooks())
                .thenReturn(createBookDTOlist());
        this.mockMvc.perform(get("/api/v1/lending/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].isbn", Matchers.is(isbn)));
    }

    @Test
    @SneakyThrows
    void getAllBooksTestNoBooksInLibraryException() {
        when(lendingService.getAllBooks())
                .thenReturn(createEmptyBookDTOlist());
        this.mockMvc.perform(get("/api/v1/lending/books"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"message\":\"В библиотеке не зарегистрировано ни одной книги.\"}"));
    }


    ///// getClientsByBookIsbn() method tests /////

    @Test
    @SneakyThrows
    void getClientsByBookIsbnTest() {
        String isbn = "not exist";
        String libraryCard = "1 lc";

        when(lendingService.getBookByISBN(isbn))
                .thenReturn(createBook());
        when(lendingService.getClientsByIsbn(isbn))
                .thenReturn(createClientDTOlist());
        this.mockMvc.perform(get("/api/v1/lending/clients/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].libraryCard", Matchers.is(libraryCard)));
    }

    @Test
    @SneakyThrows
    void getClientsByBookIsbnTestBookNotFoundException() {
        String isbn = "book does not exist";

        when(lendingService.getBookByISBN(isbn))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/v1/lending/clients/{isbn}", isbn))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"message\":\"В библиотеке не зарегистрирована книга с таким ISBN.\"}"));
    }

    @Test
    @SneakyThrows
    void getClientsByBookIsbnTestBookNotLentToAnyoneException() {
        String isbn = "not lent book";

        when(lendingService.getBookByISBN(isbn))
                .thenReturn(createBook());
        when(lendingService.getClientsByIsbn(isbn))
                .thenReturn(createEmptyClientDTOlist());
        this.mockMvc.perform(get("/api/v1/lending/clients/{isbn}", isbn))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"message\":\"В настоящее время книга с таким ISBN не выдана какому-либо клиенту.\"}"));
    }


    ///// lendBook() /////

    @Test
    @SneakyThrows
    void lendBookTest(){
        String isbn = "1 isbn";
        String libraryCard = "1 lc";
        Integer lendingTerm = 20;

        when(lendingService.getBookByISBN(isbn))
                .thenReturn(createBook());
        when(lendingService.getClientByLibraryCard(libraryCard))
                .thenReturn(createClient());
        when(lendingService.getLendingRecordByIsbnAndLibraryCard(isbn,libraryCard))
                .thenReturn(Optional.empty());
        when(lendingService.createLendingRecord(createBook().orElseThrow(), createClient().orElseThrow(), lendingTerm))
                .thenReturn(createLendingRecordDTO());
        this.mockMvc.perform(post(
                "/api/v1/lending/lend-book?isbn={isbn}&libraryCard={libraryCard}&lendingTerm={lendingTerm}",
                        isbn, libraryCard, lendingTerm))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libraryCard", Matchers.is(libraryCard)));
    }

    @Test
    @SneakyThrows
    void lendBookTestBookNotFoundException(){
        String isbn = "non-existent book";
        String libraryCard = "non-existent client";
        Integer lendingTerm = 20;

        when(lendingService.getBookByISBN(isbn))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(post(
                "/api/v1/lending/lend-book?isbn={isbn}&libraryCard={libraryCard}&lendingTerm={lendingTerm}",
                        isbn, libraryCard, lendingTerm))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"message\":\"В библиотеке не зарегистрирована книга с таким ISBN.\"}"));
    }

    @Test
    @SneakyThrows
    void lendBookTestClientNotFoundException(){
        String isbn = "1 isbn";
        String libraryCard = "non-existent client";
        Integer lendingTerm = 20;

        when(lendingService.getBookByISBN(isbn))
                .thenReturn(createBook());
        when(lendingService.getClientByLibraryCard(libraryCard))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(post(
                "/api/v1/lending/lend-book?isbn={isbn}&libraryCard={libraryCard}&lendingTerm={lendingTerm}",
                        isbn, libraryCard, lendingTerm))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"message\":\"В библиотеке не зарегистрирован клиент с таким читательским билетом.\"}"));
    }

    @Test
    @SneakyThrows
    void lendBookTestBookAlreadyLentToClientException(){
        String isbn = "1 isbn";
        String libraryCard = "1 lc";
        Integer lendingTerm = 20;

        when(lendingService.getBookByISBN(isbn))
                .thenReturn(createBook());
        when(lendingService.getClientByLibraryCard(libraryCard))
                .thenReturn(createClient());
        when(lendingService.getLendingRecordByIsbnAndLibraryCard(isbn,libraryCard)).thenReturn(createLendingRecord());
        this.mockMvc.perform(post(
                "/api/v1/lending/lend-book?isbn={isbn}&libraryCard={libraryCard}&lendingTerm={lendingTerm}",
                        isbn, libraryCard, lendingTerm))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"message\":\"Книга с таким ISBN уже выдана этому клиенту.\"}"));
    }

    @Test
    @SneakyThrows
    void lendBookTestNoCopiesLeftException(){
        String isbn = "1 isbn";
        String libraryCard = "1 lc";
        Integer lendingTerm = 20;

        when(lendingService.getBookByISBN(isbn))
                .thenReturn(createBookNoCopiesLeft());
        when(lendingService.getClientByLibraryCard(libraryCard))
                .thenReturn(createClient());
        when(lendingService.getLendingRecordByIsbnAndLibraryCard(isbn,libraryCard))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(post(
                "/api/v1/lending/lend-book?isbn={isbn}&libraryCard={libraryCard}&lendingTerm={lendingTerm}",
                        isbn, libraryCard, lendingTerm))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"message\":\"Превышено максимальное количество выданных копий книги.\"}"));
    }


    ///// returnBook() method tests /////

    @Test
    @SneakyThrows
    void returnBookTest(){
        String isbn = "1 isbn";
        String libraryCard = "1 lc";

        when(lendingService.getClientByLibraryCard(libraryCard))
                .thenReturn(createClient());
        when(lendingService.getBookByISBN(isbn))
                .thenReturn(createBook());
        when(lendingService.getLendingRecordByIsbnAndLibraryCard(isbn, libraryCard))
                .thenReturn(createLendingRecord());
        this.mockMvc.perform(delete(
                        "/api/v1/lending/return-book?isbn={isbn}&libraryCard={libraryCard}",
                        isbn, libraryCard))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"message\":\"Книга успешно возвращена.\"}"));
    }

    @Test
    @SneakyThrows
    void returnBookTestClientNotFoundException(){
        String isbn = "1 isbn";
        String libraryCard = "non-existent client";

        when(lendingService.getClientByLibraryCard(libraryCard))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(delete(
                "/api/v1/lending/return-book?isbn={isbn}&libraryCard={libraryCard}",
                        isbn, libraryCard))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"message\":\"В библиотеке не зарегистрирован клиент с таким читательским билетом.\"}"));
    }

    @Test
    @SneakyThrows
    void returnBookTestBookNotFoundException(){
        String isbn = "non-existent book";
        String libraryCard = "client";

        when(lendingService.getClientByLibraryCard(libraryCard))
                .thenReturn(createClient());
        when(lendingService.getBookByISBN(isbn))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(delete(
                        "/api/v1/lending/return-book?isbn={isbn}&libraryCard={libraryCard}",
                        isbn, libraryCard))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"message\":\"В библиотеке не зарегистрирована книга с таким ISBN.\"}"));
    }

    @Test
    @SneakyThrows
    void returnBookTestBookNotLentToClientException(){
        String isbn = "1 isbn";
        String libraryCard = "1 lc";

        when(lendingService.getClientByLibraryCard(libraryCard))
                .thenReturn(createClient());
        when(lendingService.getBookByISBN(isbn))
                .thenReturn(createBook());
        when(lendingService.getLendingRecordByIsbnAndLibraryCard(isbn, libraryCard))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(delete(
                        "/api/v1/lending/return-book?isbn={isbn}&libraryCard={libraryCard}",
                        isbn, libraryCard))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"message\":\"Книга с таким ISBN не выдана этому клиенту.\"}"));
    }
}
