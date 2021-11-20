package com.bookstore.domain.product;

import com.bookstore.domain.exception.NotFoundException;
import com.bookstore.domain.product.dto.CreateProductDto;
import com.bookstore.domain.product.dto.UpdateProductDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final MeterRegistry meterRegistry;

    public void createProduct(CreateProductDto productDto) {
        log.info("Creating product {} started", productDto);
        ProductEntity productEntity = new ProductEntity(productDto.getId(), productDto.getName(),
                productDto.getDescription(),
                productDto.getInStock(),
                productDto.getPrice());
        productRepository.save(productEntity);
        recordProductCreation();
        log.info("Creating product {} finished", productEntity);
    }

    public void deleteProduct(UUID productId) {
        log.info("Deleting product {} started", productId);
        if (findOptionalProductById(productId).isPresent()) {
            productRepository.deleteById(productId);
        }
        log.info("Deleting product {} finished", productId);
    }

    public void updateProduct(UpdateProductDto productDto) {
        log.info("Update'ing product {} started", productDto);
        ProductEntity productEntity = findProductById(productDto.getProductId());
        productEntity.updateEntity(productDto);
        productRepository.save(productEntity);
        log.info("Update'ing product {} finished", productDto.getProductId());
    }

    public ProductEntity findByIdAndInStockGreaterThanEqual(UUID productId, Integer inStock) {
        return productRepository.findByIdAndInStockGreaterThanEqual(productId, inStock).orElseThrow(
                () -> new NotFoundException("Product doesn't exist in such quantity"));
    }

    private ProductEntity findProductById(UUID productId) {
        return findOptionalProductById(productId).orElseThrow(() -> new NotFoundException("Product doesn't exist"));
    }

    private Optional<ProductEntity> findOptionalProductById(UUID productId) {
        return productRepository.findById(productId);
    }

    private void recordProductCreation() {
        Counter counter = Counter
                .builder("product_create")
                .description("indicates number of products created")
                .register(meterRegistry);
        counter.increment();
    }
}
