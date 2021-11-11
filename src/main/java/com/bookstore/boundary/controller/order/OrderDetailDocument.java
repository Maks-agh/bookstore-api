package com.bookstore.boundary.controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailDocument {

    @JsonProperty("productId")
    private final UUID productId;

    @JsonProperty("productName")
    private final String productName;

    @JsonProperty("quantity")
    private final Integer quantity;

    @JsonProperty("price")
    private final Double price;
}
