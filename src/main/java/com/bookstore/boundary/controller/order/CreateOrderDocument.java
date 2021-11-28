package com.bookstore.boundary.controller.order;

import com.bookstore.boundary.controller.customer.AddressDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @JsonProperty("customer")
    private final CreateOrderDocument.CustomerDocument customer;

    @Getter
    @Valid
    @RequiredArgsConstructor
    public static class CustomerDocument {

        @NotNull
        @JsonProperty("id")
        private final UUID id;

        @NotBlank
        @JsonProperty("name")
        private final String name;

        @NotBlank
        @JsonProperty("email")
        private final String email;

        @NotBlank
        @JsonProperty("phone")
        private final String phone;

        @NotNull
        @Valid
        @JsonProperty("address")
        private final AddressDocument address;

    }
}
