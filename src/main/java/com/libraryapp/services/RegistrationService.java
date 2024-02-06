package com.libraryapp.services;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.models.Book;
import com.libraryapp.models.Client;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {

    @Transactional
    Book createBook(BookDTO bookDTO);

    @Transactional
    Client createClient(ClientDTO clientDTO);
}
