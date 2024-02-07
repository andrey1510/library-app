package com.libraryapp.testdata;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.dto.LendingRecordDTO;
import com.libraryapp.models.Book;
import com.libraryapp.models.Client;
import com.libraryapp.models.LendingRecord;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class TestData {

    protected List<BookDTO> createBookDTOlist() {
        List<BookDTO> testList = new ArrayList<>();
        testList.add(new BookDTO("1 isbn",
                "автор 1",
                "книга 1",
                LocalDate.parse("2022-02-25"),
                10,
                0));
        testList.add(new BookDTO("2 isbn",
                "автор 2",
                "книга 2",
                LocalDate.parse("2022-02-25"),
                5,
                0));
        testList.add(new BookDTO("3 isbn",
                "автор 3",
                "книга 3",
                LocalDate.parse("2021-02-25"),
                1,
                0));
        return testList;
    }

    protected List<LendingRecord> createLendingRecordlist() {
        List<LendingRecord> testList = new ArrayList<>();
        testList.add(new LendingRecord(createBook().orElseThrow(),
                createClient().orElseThrow(),
                LocalDateTime.now()));
        testList.add(new LendingRecord(createBookNoCopiesLeft().orElseThrow(),
                createClient().orElseThrow(),
                LocalDateTime.now()));
        return testList;
    }

    protected List<BookDTO> createEmptyBookDTOlist() {
        return new ArrayList<>();
    }

    protected List<ClientDTO> createClientDTOlist() {
        List<ClientDTO> testList = new ArrayList<>();
        testList.add(new ClientDTO("1 lc", "клиент 1"));
        testList.add(new ClientDTO("2 lc", "клиент 2"));
        return testList;
    }

    protected List<ClientDTO> createEmptyClientDTOlist() {
        return new ArrayList<>();
    }

    protected Optional<Book> createBook() {
        return Optional.of(new Book("2 isbn",
                "автор 2",
                "книга 2",
                LocalDate.parse("2022-02-25"),
                5,
                0,
                null));
    }

    protected Optional<Book> createBookNoCopiesLeft() {
        return Optional.of(new Book("2 isbn",
                "автор 2",
                "книга 2",
                LocalDate.parse("2022-02-25"),
                1,
                1,
                null));
    }

    protected Optional<Client> createClient() {
        return Optional.of(new Client("1 lc", "клиент 1", null));
    }


    protected Optional<LendingRecord> createLendingRecord() {
        return Optional.of(new LendingRecord(createBook().orElseThrow(), createClient().orElseThrow()));
    }

    protected LendingRecordDTO createLendingRecordDTO() {
        return new LendingRecordDTO("1 isbn", "1 lc", LocalDate.parse("2024-02-07"));
    }
}
