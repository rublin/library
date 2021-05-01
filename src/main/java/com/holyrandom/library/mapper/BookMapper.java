package com.holyrandom.library.mapper;

import com.holyrandom.library.dto.BookDetailsDto;
import com.holyrandom.library.dto.BookSmallDto;
import com.holyrandom.library.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    BookSmallDto toSmall(Book book);

    BookDetailsDto toDetails(Book book);
}
