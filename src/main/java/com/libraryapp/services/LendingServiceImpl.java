package com.libraryapp.services;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.dto.LendingRecordDTO;
import com.libraryapp.mappers.BookMapper;
import com.libraryapp.mappers.ClientMapper;
import com.libraryapp.mappers.LendingRecordMapper;
import com.libraryapp.models.Book;
import com.libraryapp.models.LendingRecord;
import com.libraryapp.models.Client;
import com.libraryapp.repositories.LendingRecordRepository;
import com.libraryapp.repositories.BookRepository;
import com.libraryapp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.libraryapp.utilities.LendingTermGenerator.generateLendingTerm;

@Service
public class LendingServiceImpl implements LendingService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LendingRecordRepository lendingRecordRepository;

    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private LendingRecordMapper lendingRecordMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> getClientsByIsbn(String isbn) {
        return clientRepository.findClientsByIsbn(isbn).stream()
                .map(clientMapper::clientToClientDTO)
                .collect(Collectors.toList());
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
    public LendingRecordDTO createLendingRecord(Book book, Client client, Integer lendingTerm) {
        LendingRecord lendingRecord = lendingRecordRepository
                .save(new LendingRecord(book, client, generateLendingTerm(lendingTerm)));
        return lendingRecordMapper.lendingRecordToLendingRecordDTO(lendingRecord);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        lendingRecordRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateLentCopies(int lentCopies, String isbn) {
        bookRepository.updateLentCopies(lentCopies, isbn);
    }

}
