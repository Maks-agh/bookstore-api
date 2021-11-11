package com.bookstore.boundary.controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderProductDocument {

    @NotNull
    @JsonProperty("productId")
    private final UUID productId;

    @NotNull
    @JsonProperty("quantity")
    private final Integer quantity;

}
