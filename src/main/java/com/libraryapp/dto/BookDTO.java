package com.libraryapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    @NotNull
    @Schema(requiredMode = REQUIRED,
            example = "978-5-4335-0977-1",
            description = "ISBN книги.")
    private String isbn;

    @NotNull
    @Schema(requiredMode = REQUIRED,
            example = "Булгаков Михаил Афанасьевич",
            description = "ФИО автора.")
    private String author;

    @NotNull
    @Schema(requiredMode = REQUIRED,
            example = "Мастер и Маргарита",
            description = "Название книги.")
    private String title;

    @NotNull
    @Schema(requiredMode = REQUIRED,
            example = "01.08.2022",
            description = "ФИО клиента.")
    private Date publicationDate;

    @Schema(requiredMode = REQUIRED,
            example = "1",
            description = "Максимальное количество копий книги, которое можно выдавать.")
    private int maxCopies;

    @Schema(requiredMode = REQUIRED,
            example = "1",
            description = "Максимальное количество выданных клиентам копий книги.")
    private int lentCopies;

}
