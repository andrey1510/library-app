package com.libraryapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    @NotNull
    @Schema(requiredMode = REQUIRED,
            example = "4300312038",
            description = "Номер библиотечной карточки клиента.")
    private String libraryCard;

    @NotNull
    @Schema(requiredMode = REQUIRED,
            example = "Петров Петр Петрович",
            description = "ФИО клиента.")
    private String fullName;

}
