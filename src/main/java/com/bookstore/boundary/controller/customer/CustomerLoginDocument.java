package com.bookstore.boundary.controller.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerLoginDocument {

    @NotBlank
    @JsonProperty("email")
    private final String email;

    @NotBlank
    @JsonProperty("password")
    private final String password;

}
