package com.bookstore.domain.customer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository {

    CustomerEntity save(CustomerEntity customerEntity);

    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findById(UUID id);

    @Query("SELECT c FROM CustomerEntity c WHERE c.id = ?1 AND c.password = null")
    Optional<CustomerEntity> findUnregisteredById(UUID id);
}
