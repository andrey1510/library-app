package com.libraryapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "book")
public class Book {

    @NotNull
    @Id
    @Column(name = "isbn", nullable = false)
    @Schema(requiredMode = REQUIRED,
            example = "978-5-4335-0977-1",
            description = "ISBN книги.")
    private String isbn;

    @NotNull
    @Column(name = "author", nullable = false)
    @Schema(requiredMode = REQUIRED,
            example = "Булгаков Михаил Афанасьевич",
            description = "ФИО автора.")
    private String author;

    @NotNull
    @Column(name = "title", nullable = false)
    @Schema(requiredMode = REQUIRED,
            example = "Мастер и Маргарита",
            description = "Название книги.")
    private String title;

    @NotNull
    @Column(name = "publication_date", nullable = false)
    @Schema(requiredMode = REQUIRED,
            example = "01.08.2022",
            description = "ФИО клиента.")
    private Date publicationDate;

    @Column(name = "max_copies", columnDefinition = "integer default 10")
    @Schema(requiredMode = REQUIRED,
            example = "10",
            description = "Максимальное количество копий книги, которое можно выдавать.")
    private int maxCopies;

    @Column(name = "lent_copies")
    @Schema(requiredMode = REQUIRED,
            example = "0",
            description = "Максимальное количество выданных клиентам копий книги.")
    private int lentCopies;

    @OneToMany(mappedBy = "book")
    private Set<LendingRecord> lendingRecords = new HashSet<>();


}
