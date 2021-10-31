package com.bookstore.boundary.controller.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerRegistrationDocument {

    @NotBlank
    @JsonProperty("name")
    private final String name;

    @NotBlank
    @JsonProperty("email")
    private final String email;

    @NotBlank
    @JsonProperty("phone")
    private final String phone;

    @NotBlank
    @JsonProperty("password")
    private final String password;

    @NotBlank
    @JsonProperty("repeatPassword")
    private final String repeatPassword;

    @NotNull
    @Valid
    @JsonProperty("address")
    private final AddressDocument address;

}
