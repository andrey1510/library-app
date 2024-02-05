package com.libraryapp.mappers;

import com.libraryapp.dto.ClientDTO;
import com.libraryapp.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "libraryCard", source = "libraryCard")
    @Mapping(target = "fullName", source = "fullName")
    ClientDTO clientToClientDTO(Client client);

}
