package com.bookstore.domain.product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    ProductEntity save(ProductEntity productEntity);

    void deleteById(UUID productId);

    Optional<ProductEntity> findById(UUID productId);

    Optional<ProductEntity> findByIdAndInStockGreaterThanEqual(UUID productId, Integer inStock);

}
