package com.holyrandom.library.controller;

import com.holyrandom.library.dto.ExpiredInUseDto;
import com.holyrandom.library.entity.BookHistory;
import com.holyrandom.library.entity.BookInUse;
import com.holyrandom.library.mapper.BookInUseMapper;
import com.holyrandom.library.service.BookUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookUsageController {

    private final BookUsageService bookUsageService;
    private final BookInUseMapper bookInUseMapper;

    @GetMapping("/usage/expired")
    public List<ExpiredInUseDto> expired(@RequestParam(defaultValue = "${time.expired.after}") Integer minExpiredDays) {
        List<BookInUse> expired = bookUsageService.getExpired(minExpiredDays);

        return expired.stream()
                .map(bookInUseMapper::toDto)
                .collect(Collectors.toList());
    }

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
