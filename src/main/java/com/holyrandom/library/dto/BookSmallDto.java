package com.holyrandom.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookSmallDto {
    private Long id;
    private String name;
    private String author;
    private String description;
    private String publisher;
    private String isbn;
    private Integer year;
    private boolean available;
}
