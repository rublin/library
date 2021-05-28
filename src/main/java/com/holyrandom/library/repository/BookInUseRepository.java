package com.holyrandom.library.repository;

import com.holyrandom.library.entity.Book;
import com.holyrandom.library.entity.BookInUse;
import com.holyrandom.library.entity.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookInUseRepository extends PagingAndSortingRepository<BookInUse, Long> {

    Optional<BookInUse> findByClientAndBook(Client client, Book book);

    List<BookInUse> findByInUseFromBefore(LocalDate inUseFromBefore);
}
