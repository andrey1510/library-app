package com.libraryapp.services;

import com.libraryapp.dto.ClientDTO;
import com.libraryapp.models.Book;
import com.libraryapp.models.LendingRecord;
import com.libraryapp.models.Client;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LendingService {

    List<Book> getAllBooks();

    List<ClientDTO> getClientsByBookIsbn(String isbn);

    Optional<Book> getBookByISBN(String isbn);

    Optional<Client> getClientByLibraryCard(String libraryCard);

    @Transactional(readOnly = true)
    Optional<LendingRecord> getLendingRecordByIsbnAndLibraryCard(String isbn, String libraryCard);

    @Transactional
    LendingRecord createBookLending(LendingRecord lendingRecord);


    @Transactional
    void deleteById(Long id);

    @Transactional
    void updateLentCopies(int lentCopies);
}
