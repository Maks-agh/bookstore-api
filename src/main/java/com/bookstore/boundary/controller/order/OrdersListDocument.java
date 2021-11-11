package com.bookstore.boundary.controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrdersListDocument {

    @JsonProperty("orders")
    private final List<OrderDocument> orders;

}
