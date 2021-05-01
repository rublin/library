package com.holyrandom.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookInUseDto {
    private BookSmallDto book;
    private LocalDate inUseFrom;
}
