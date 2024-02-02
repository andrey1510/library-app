package com.libraryapp.services;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.Client;

import java.util.List;

public interface LendingService {

    List<Book> getAllBooks();

    List<Client> getClientsByBookIsbn(String isbn);
}
