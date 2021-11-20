package com.bookstore.domain.product.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CreateProductDto {

    private final UUID id;

    private final String name;

    private final String description;

    private final Integer inStock;

    private final Double price;

}
