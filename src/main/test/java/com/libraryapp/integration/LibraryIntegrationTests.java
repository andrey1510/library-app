package com.libraryapp.integration;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.dto.LendingRecordDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LibraryIntegrationTests {

    private final String uri = "http://localhost:8080/api/v1/lending/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllBooksTest() {
        String isbn = "1 isbn";

        ResponseEntity<List<BookDTO>> responseEntity = restTemplate.exchange(
                uri + "books",
                HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(isbn, responseEntity.getBody().stream().findFirst().orElseThrow().getIsbn());
    }

    @Test
    void lendBookTest() {
        String isbn = "1 isbn";
        String libraryCard = "1 lc";
        Integer lendingTerm = 10;

        ResponseEntity<LendingRecordDTO> responseEntity = restTemplate.postForEntity(
                uri + "lend-book?isbn={isbn}&libraryCard={libraryCard}&lendingTerm={lendingTerm}",
                null, LendingRecordDTO.class, isbn, libraryCard, lendingTerm);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(isbn, responseEntity.getBody().getIsbn());
    }

    @Test
    void returnBookTest() {
        String isbn = "2 isbn";
        String libraryCard = "2 lc";
        Integer lendingTerm = 5;

        ResponseEntity<LendingRecordDTO> responseEntity = restTemplate.postForEntity(
                uri + "lend-book?isbn={isbn}&libraryCard={libraryCard}&lendingTerm={lendingTerm}",
                null, LendingRecordDTO.class, isbn, libraryCard, lendingTerm);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(isbn, responseEntity.getBody().getIsbn());

        ResponseEntity<String> responseEntityDelete = restTemplate.exchange(
                uri + "return-book?isbn={isbn}&libraryCard={libraryCard}",
                HttpMethod.DELETE,
                null, String.class, isbn, libraryCard);
        assertEquals(HttpStatus.OK, responseEntityDelete.getStatusCode());
    }

    @Test
    void getClientByIsbnTest() {
        String isbn = "3 isbn";
        String libraryCard = "3 lc";
        Integer lendingTerm = 10;

        ResponseEntity<LendingRecordDTO> responseEntity = restTemplate.postForEntity(
                uri + "lend-book?isbn={isbn}&libraryCard={libraryCard}&lendingTerm={lendingTerm}",
                null, LendingRecordDTO.class, isbn, libraryCard, lendingTerm);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(isbn, responseEntity.getBody().getIsbn());

        ResponseEntity<List<ClientDTO>> responseEntityGet = restTemplate.exchange(
                uri + "clients/{isbn}",
                HttpMethod.GET,
                null, new ParameterizedTypeReference<>(){}, isbn);

        assertEquals(HttpStatus.OK, responseEntityGet.getStatusCode());
        assertEquals(libraryCard, responseEntityGet.getBody().stream().findFirst().orElseThrow().getLibraryCard());
    }

}



