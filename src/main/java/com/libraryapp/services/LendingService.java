package com.libraryapp.services;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.LendingRecord;
import com.libraryapp.entities.Client;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LendingService {

    List<Book> getAllBooks();

    List<Client> getClientsByBookIsbn(String isbn);

    Optional<Book> getBookByISBN(String isbn);

    Optional<Client> getClientByLibraryCard(String libraryCard);

    @Transactional(readOnly = true)
    Optional<LendingRecord> getLendingRecordByIsbnAndLibraryCard(String isbn, String libraryCard);

    @Transactional
    LendingRecord createBookLending(LendingRecord lendingRecord);


    @Transactional
    void deleteById(Long id);
}
