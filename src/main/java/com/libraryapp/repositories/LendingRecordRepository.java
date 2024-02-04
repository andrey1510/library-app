package com.libraryapp.repositories;

import com.libraryapp.entities.LendingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LendingRecordRepository extends JpaRepository<LendingRecord, Long> {

    Optional<LendingRecord> findLendingRecordByBook_IsbnAndClient_LibraryCard(String isbn, String libraryCard);
}
