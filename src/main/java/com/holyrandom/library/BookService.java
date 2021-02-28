package com.holyrandom.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book get(Long id) {
        Optional<Book> optional = bookRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new RuntimeException("Book not found");
    }

    public Page<Book> getAll(String query, Pageable pageable) {
        if (query != null) {
            return bookRepository.findByQuery( "%" + query.toLowerCase() + "%", pageable);
        }
        Page<Book> page = bookRepository.findAll(pageable);
        return page;
    }

    public Book create(Book book) {
        Book saved = bookRepository.save(book);
        return saved;
    }

    public Book update(Long id, Book book) {
        Book original = get(id);
        original.setName(book.getName());
        original.setAuthor(book.getAuthor());
        original.setDescription(book.getDescription());
        original.setPublisher(book.getPublisher());
        original.setIsbn(book.getIsbn());
        original.setYear(book.getYear());

        Book updated = bookRepository.save(original);
        return updated;
    }

    public void delete(Long id) {
        Book book = get(id);
        bookRepository.delete(book);
    }
}
