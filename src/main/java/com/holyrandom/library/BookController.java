package com.holyrandom.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.get(id);
    }

    @PostMapping("/book")
    public Book createBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping("/book/{id}")
    public Book editBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
