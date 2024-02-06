package com.libraryapp.models;

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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


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

    @Column(name = "timestamp", nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp issuanceTimestamp;

    @Column(name = "lending_term", columnDefinition = "integer default 30")
    private Integer lendingTerm;


    public LendingRecord(Book book, Client client, int lendingTerm) {
        this.book = book;
        this.client = client;
        this.lendingTerm = lendingTerm;
    }

}
