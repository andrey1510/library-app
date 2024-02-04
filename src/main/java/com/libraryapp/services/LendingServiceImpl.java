package com.libraryapp.services;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.LendingRecord;
import com.libraryapp.entities.Client;
import com.libraryapp.repositories.LendingRecordRepository;
import com.libraryapp.repositories.BookRepository;
import com.libraryapp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LendingServiceImpl implements LendingService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LendingRecordRepository lendingRecordRepository;


    //ToDo отрезать relations
    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getClientsByBookIsbn(String isbn) {
        return bookRepository.getClientsByBookIsbn(isbn);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getBookByISBN(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> getClientByLibraryCard(String libraryCard) {
        return clientRepository.findById(libraryCard);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LendingRecord> getLendingRecordByIsbnAndLibraryCard(String isbn, String libraryCard) {
        return lendingRecordRepository.findLendingRecordByBook_IsbnAndClient_LibraryCard(isbn, libraryCard);
    }

    @Override
    @Transactional
    public LendingRecord createBookLending(LendingRecord lendingRecord) {
        return lendingRecordRepository.save(lendingRecord);
    }
    @Override
    @Transactional
    public void deleteById(Long id){
        lendingRecordRepository.deleteById(id);
    }

}
