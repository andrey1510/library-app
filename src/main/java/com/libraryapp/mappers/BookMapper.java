package com.libraryapp.mappers;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "publicationDate", source = "publicationDate")
    @Mapping(target = "maxCopies", source = "maxCopies")
    @Mapping(target = "lentCopies", source = "lentCopies")
    BookDTO bookToBookDTO(Book book);

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "publicationDate", source = "publicationDate")
    @Mapping(target = "maxCopies", source = "maxCopies")
    @Mapping(target = "lentCopies", source = "lentCopies")
    Book bookDTOToBook(BookDTO bookDTO);
}
