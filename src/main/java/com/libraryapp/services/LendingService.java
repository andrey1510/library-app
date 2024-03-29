package com.libraryapp.services;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.dto.LendingRecordDTO;
import com.libraryapp.models.Book;
import com.libraryapp.models.LendingRecord;
import com.libraryapp.models.Client;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LendingService {

    List<BookDTO> getAllBooks();

    List<ClientDTO> getClientsByIsbn(String isbn);

    Optional<Book> getBookByISBN(String isbn);

    Optional<Client> getClientByLibraryCard(String libraryCard);

    @Transactional(readOnly = true)
    Optional<LendingRecord> getLendingRecordByIsbnAndLibraryCard(String isbn, String libraryCard);

    @Transactional
    LendingRecordDTO createLendingRecord(Book book, Client client, Integer lendingTerm);

    @Transactional
    void deleteById(Long id);

    @Transactional
    void updateLentCopies(int lentCopies, String isbn);
}
