package com.holyrandom.library;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    private String description;
    private String publisher;
    private String isbn;
    private Integer year;

    public Book(BookDto dto) {
        name = dto.getName();
        author = dto.getAuthor();
        description = dto.getDescription();
        publisher = dto.getPublisher();
        isbn = dto.getIsbn();
        year = dto.getYear();
    }
}
