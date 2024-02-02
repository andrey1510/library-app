package com.libraryapp.services;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.Client;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {
    @Transactional
    Book registerBook(Book book);

    @Transactional
    Client registerClient(Client client);
}
