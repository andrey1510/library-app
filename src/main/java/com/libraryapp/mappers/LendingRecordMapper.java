package com.libraryapp.mappers;

import com.libraryapp.dto.LendingRecordDTO;
import com.libraryapp.models.LendingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LendingRecordMapper {

    @Mapping(target = "isbn", source = "book.isbn")
    @Mapping(target = "libraryCard", source = "client.libraryCard")
    @Mapping(target = "lendingTerm", source = "lendingTerm", defaultValue = "30")
    LendingRecordDTO lendingRecordToLendingRecordDTO(LendingRecord lendingRecord);

}
