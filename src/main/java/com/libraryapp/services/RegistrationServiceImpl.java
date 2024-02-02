package com.libraryapp.services;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.Client;
import com.libraryapp.repositories.BookRepository;
import com.libraryapp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Book registerBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Client registerClient(Client client) {
        return clientRepository.save(client);
    }
}
