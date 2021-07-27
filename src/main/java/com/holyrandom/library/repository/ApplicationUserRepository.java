package com.holyrandom.library.repository;

import com.holyrandom.library.entity.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByEmail(String email);
}
