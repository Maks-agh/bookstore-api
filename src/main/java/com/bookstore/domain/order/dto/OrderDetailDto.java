package com.bookstore.domain.order.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class OrderDetailDto {

    private final UUID productId;

    private final String productName;

    private final Integer quantity;

    private final Double price;
}
