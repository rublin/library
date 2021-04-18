package com.holyrandom.library.repository;

import com.holyrandom.library.entity.BookHistory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookHistoryRepository extends PagingAndSortingRepository<BookHistory, Long> {
}
