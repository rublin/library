package com.holyrandom.library.controller;

import com.holyrandom.library.entity.BookHistory;
import com.holyrandom.library.entity.BookInUse;
import com.holyrandom.library.service.BookUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookUsageController {

    private final BookUsageService bookUsageService;

    @PutMapping("/usage/client/{clientId}/book/{bookId}")
    public BookInUse takeBook(@PathVariable Long clientId,
                              @PathVariable Long bookId) {
        return bookUsageService.takeBook(clientId, bookId);
    }

    @DeleteMapping("/usage/client/{clientId}/book/{bookId}")
    public BookHistory returnBook(@PathVariable Long clientId,
                                  @PathVariable Long bookId) {
        return bookUsageService.returnBook(clientId, bookId);
    }
}
