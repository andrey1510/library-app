package com.libraryapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "book_lending")
public class LendingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne()
    @JoinColumn(name="isbn")
    private Book book;

    @ManyToOne()
    @JoinColumn(name="library_card")
    private Client client;

    @NotNull
    @Column(name = "lending_term", nullable = false)
    @Schema(description = "Дата и время окончания срока выдачи книги.")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lendingTerm;


    public LendingRecord(Book book, Client client) {
        this.book = book;
        this.client = client;
    }

    public LendingRecord(Book book, Client client, LocalDateTime lendingTerm) {
        this.book = book;
        this.client = client;
        this.lendingTerm = lendingTerm;
    }
}
