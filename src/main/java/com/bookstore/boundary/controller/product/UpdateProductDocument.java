package com.bookstore.boundary.controller.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateProductDocument {

    @NotBlank
    @JsonProperty("name")
    private final String name;

    @NotBlank
    @JsonProperty("description")
    private final String description;

    @NotNull
    @PositiveOrZero
    @JsonProperty("inStock")
    private final Double inStock;

    @NotNull
    @Positive
    @JsonProperty("price")
    private final Double price;

}
