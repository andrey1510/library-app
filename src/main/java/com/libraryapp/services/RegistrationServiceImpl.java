package com.libraryapp.services;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.mappers.BookMapper;
import com.libraryapp.mappers.ClientMapper;
import com.libraryapp.models.Book;
import com.libraryapp.models.Client;
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

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ClientMapper clientMapper;


    @Override
    @Transactional
    public Book createBook(BookDTO bookDTO) {
        return bookRepository.save(bookMapper.bookDTOToBook(bookDTO));
    }

    @Override
    @Transactional
    public Client createClient(ClientDTO clientDTO) {
        return clientRepository.save(clientMapper.clientDTOToClient(clientDTO));
    }
}
