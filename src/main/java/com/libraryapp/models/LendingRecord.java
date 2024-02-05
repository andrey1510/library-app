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
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

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
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @ManyToOne()
    @JoinColumn(name="isbn")
    private Book book;

    @ManyToOne()
    @JoinColumn(name="library_card")
    private Client client;

    @Column(name = "timestamp", nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Timestamp issuanceTimestamp;

    @NotBlank
    @Column(name = "max_copies", columnDefinition = "integer default 30")
    @Schema(requiredMode = REQUIRED,
            example = "10",
            description = "Количество дней, на которое выдается книга.")
    private int maxCopies;


    public LendingRecord(Book book, Client client) {
        this.book = book;
        this.client = client;
    }
}
