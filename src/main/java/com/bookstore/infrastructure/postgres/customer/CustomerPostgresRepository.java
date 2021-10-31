package com.bookstore.infrastructure.postgres.customer;

import com.bookstore.domain.customer.CustomerEntity;
import com.bookstore.domain.customer.CustomerRepository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPostgresRepository extends CustomerRepository, JpaRepository<CustomerEntity, UUID> {

    CustomerEntity save(CustomerEntity customerEntity);

    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findById(UUID id);
}
