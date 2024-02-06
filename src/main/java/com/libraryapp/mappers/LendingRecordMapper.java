package com.libraryapp.mappers;

import com.libraryapp.dto.LendingRecordDTO;
import com.libraryapp.models.LendingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LendingRecordMapper {

    LendingRecordMapper INSTANCE = Mappers.getMapper(LendingRecordMapper.class);

    @Mapping(target = "isbn", source = "book.isbn")
    @Mapping(target = "libraryCard", source = "client.libraryCard")
    @Mapping(target = "lendingTerm", source = "lendingTerm", defaultValue = "30")
    LendingRecordDTO lendingRecordToLendingRecordDTO(LendingRecord lendingRecord);

}
