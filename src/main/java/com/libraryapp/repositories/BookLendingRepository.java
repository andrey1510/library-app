package com.libraryapp.repositories;

import com.libraryapp.entities.BookLending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookLendingRepository extends JpaRepository<BookLending, UUID> {
}
