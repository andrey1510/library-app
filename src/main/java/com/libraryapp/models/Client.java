package com.libraryapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "client")
public class Client {

    //ToDo
    @NotNull
    @Id
    @Column(name = "library_card", nullable = false)
    @Schema(requiredMode = REQUIRED,
            example = "4300312038",
            description = "Номер библиотечной карточки клиента.")
    private String libraryCard;

    @NotNull
    @Column(name = "full_name", nullable = false)
    @Schema(requiredMode = REQUIRED,
            example = "Петров Петр Петрович",
            description = "ФИО клиента.")
    private String fullName;

    @OneToMany(mappedBy = "client")
    private Set<LendingRecord> lendingRecords = new HashSet<>();

}
