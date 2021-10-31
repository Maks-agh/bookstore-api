package com.bookstore.boundary.controller.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorizationDocument {

    @JsonProperty("token")
    private final String token;
}
