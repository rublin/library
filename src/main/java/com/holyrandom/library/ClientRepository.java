package com.holyrandom.library;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
    Optional<Client> findByEmailOrPhone(String email, String phone);

    @Query("select client from Client  client where lower(client.firstName) like :query " +
            "or lower(client.lastName) like :query " +
            "or lower(client.email) like :query " +
            "or lower(client.phone) like :query")
    Page<Client> findByQuery(String query, Pageable pageable);
}
