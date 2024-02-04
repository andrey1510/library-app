package com.libraryapp.repositories;

import com.libraryapp.entities.Book;
import com.libraryapp.entities.Client;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("select " +
            "c.libraryCard, " +
            "c.fullName " +
            "from " +
            "LendingRecord l " +
            "left join Client c on l.client.libraryCard = c.libraryCard " +
            "left join Book b on l.book.isbn = b.isbn " +
            "where b.isbn = :isbn " +
            "order by " +
            "c.fullName asc ")
    List<Client> getClientsByBookIsbn(@Param("isbn") String isbn);
//
//    Book findByIsbn(String isbn);

}
