package com.libraryapp.repositories;

import com.libraryapp.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    @Query("select c from Client c join c.lendingRecords l WHERE l.book.isbn = :isbn")
    List<Client> findClientsByIsbn(String isbn);

//
//    Client findClientByLibraryCard(String libraryCard);
}
