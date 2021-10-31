package com.bookstore.boundary.controller.product;

import com.bookstore.domain.product.dto.CreateProductDto;
import com.bookstore.domain.product.dto.UpdateProductDto;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResourceFactory {

    static CreateProductDto toProductDto(CreateProductDocument productDocument) {
        return new CreateProductDto(productDocument.getName(),
                productDocument.getDescription(),
                productDocument.getInStock(),
                productDocument.getPrice());
    }

    static UpdateProductDto toUpdateProductDto(UUID productId, UpdateProductDocument productDocument) {
        return new UpdateProductDto(productId,
                productDocument.getName(),
                productDocument.getDescription(),
                productDocument.getInStock(),
                productDocument.getPrice());
    }

}
