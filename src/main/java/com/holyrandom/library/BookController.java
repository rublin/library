package com.holyrandom.library;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/book")
    public String getAllBooks() {
        return "All the books";
    }

    @GetMapping("/book/{id}")
    public String getBookById(@PathVariable Long id) {
        return "Book by id " + id;
    }

    @PostMapping("/book")
    public String createBook() {
        return "Create a book";
    }

    @PutMapping("/book/{id}")
    public String editBook(@PathVariable Long id) {
        return "Edit book by id " + id;
    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable Long id) {
        return "Delete a book by id " + id;
    }
}
