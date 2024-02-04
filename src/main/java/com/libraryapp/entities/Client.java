package com.libraryapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @Id
    @Column(name = "library_card", nullable = false)
    @Schema(requiredMode = REQUIRED,
            example = "4300312038",
            description = "Номер библиотечной карточки клиента.")
    private String libraryCard;

    @NotBlank
    @Column(name = "full_name", nullable = false)
    @Schema(requiredMode = REQUIRED,
            example = "Петров Петр Петрович",
            description = "ФИО клиента.")
    private String fullName;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    private Set<LendingRecord> lendingRecords = new HashSet<>();

    public Client(String libraryCard, String fullName) {
        this.libraryCard = libraryCard;
        this.fullName = fullName;
    }
}
