package com.bookstore.infrastructure.postgres.product;

import com.bookstore.domain.product.ProductEntity;
import com.bookstore.domain.product.ProductRepository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPostgresRepository extends ProductRepository, JpaRepository<ProductEntity, UUID> {

    ProductEntity save(ProductEntity productEntity);

    void deleteById(UUID productId);

    Optional<ProductEntity> findById(UUID productId);

    Optional<ProductEntity> findByIdAndInStockGreaterThanEqual(UUID productId, Integer inStock);
}
