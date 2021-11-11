package com.bookstore.domain.order.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CreateOrderProductDto {

    private final UUID productId;

    private final Integer quantity;

}
