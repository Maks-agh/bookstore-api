package com.bookstore.domain.customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    CustomerEntity save(CustomerEntity customerEntity);

    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findById(UUID id);
}
