package com.bookstore.boundary.controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderDocument {

    @NotEmpty
    @JsonProperty("products")
    private final List<CreateOrderProductDocument> products;

    @JsonProperty("customerId")
    private final UUID customerId;

}
