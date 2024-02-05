package com.libraryapp.repositories;

import com.libraryapp.models.LendingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LendingRecordRepository extends JpaRepository<LendingRecord, Long> {

    Optional<LendingRecord> findLendingRecordByBook_IsbnAndClient_LibraryCard(String isbn, String libraryCard);
}
