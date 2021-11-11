package com.bookstore.domain.order.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CreateOrderDto {

    private final List<CreateOrderProductDto> products;

    private final UUID customerId;
}
