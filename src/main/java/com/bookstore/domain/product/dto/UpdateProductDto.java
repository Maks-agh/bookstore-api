package com.bookstore.domain.product.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class UpdateProductDto {

    private final UUID productId;

    private final String name;

    private final String description;

    private final Double inStock;

    private final Double price;

}
