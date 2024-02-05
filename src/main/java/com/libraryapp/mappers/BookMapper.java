package com.libraryapp.mappers;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.models.Book;
import com.libraryapp.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "publicationDate", source = "publicationDate")
    @Mapping(target = "maxCopies", source = "maxCopies")
    @Mapping(target = "lentCopies", source = "lentCopies")
    BookDTO clientToClientDTO(Book book);

}
