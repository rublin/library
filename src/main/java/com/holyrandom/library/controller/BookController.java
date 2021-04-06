package com.holyrandom.library.controller;

import com.holyrandom.library.dto.BookDto;
import com.holyrandom.library.entity.Book;
import com.holyrandom.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public Page<Book> getAllBooks(@RequestParam Integer page,
                                  @RequestParam Integer size,
                                  @RequestParam(required = false) String query) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getAll(query, pageable);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.get(id);
    }

    @PostMapping("/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody BookDto dto) {
        return bookService.create(dto);
    }

    @PutMapping("/book/{id}")
    public Book editBook(@PathVariable Long id, @Valid @RequestBody BookDto dto) {
        return bookService.update(id, dto);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
