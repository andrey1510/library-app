package com.libraryapp.services;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.Client;
import com.libraryapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendingServiceImpl implements LendingService {

    @Autowired
    private BookRepository bookRepository;


    //ToDo отрезать relations
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Client> getClientsByBookIsbn(String isbn) {
        return bookRepository.getClientsByBookIsbn(isbn);
    }

}
