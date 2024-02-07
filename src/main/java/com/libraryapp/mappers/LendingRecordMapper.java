package com.libraryapp.mappers;

import com.libraryapp.dto.LendingRecordDTO;
import com.libraryapp.models.LendingRecord;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LendingRecordMapper {

    @Mapping(target = "isbn", source = "book.isbn")
    @Mapping(target = "libraryCard", source = "client.libraryCard")
    @Mapping(target = "lendingTerm", source = "lendingTerm")
    LendingRecordDTO lendingRecordToLendingRecordDTO(LendingRecord lendingRecord);

    @AfterMapping
    default void convertLendingTerm(LendingRecord lendingRecord, @MappingTarget LendingRecordDTO lendingRecordDTO) {
        if (lendingRecord.getLendingTerm() != null) {
            lendingRecordDTO.setLendingTerm(lendingRecord.getLendingTerm().toLocalDate());
        }
    }

}
