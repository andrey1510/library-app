package com.libraryapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LendingRecordDTO {

    @NotNull
    @Schema(requiredMode = REQUIRED,
            example = "978-5-4335-0977-1",
            description = "ISBN книги.")
    private String isbn;

    @NotNull
    @Schema(requiredMode = REQUIRED,
            example = "4300312038",
            description = "Номер библиотечной карточки клиента.")
    private String libraryCard;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    @Schema(requiredMode = REQUIRED,
            description = "Дата и время окончания срока выдачи книги.")
    private Timestamp lendingTerm;

}
