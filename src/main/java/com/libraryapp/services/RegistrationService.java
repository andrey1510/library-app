package com.libraryapp.services;

import com.libraryapp.models.Book;
import com.libraryapp.models.Client;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {
    @Transactional
    Book createBook(Book book);

    @Transactional
    Client createClient(Client client);
}
