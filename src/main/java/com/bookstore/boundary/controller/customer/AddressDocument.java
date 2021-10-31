package com.bookstore.boundary.controller.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddressDocument {

    @NotBlank
    @JsonProperty("firstLine")
    private final String firstLine;

    @NotBlank
    @JsonProperty("secondLine")
    private final String secondLine;

    @NotBlank
    @JsonProperty("city")
    private final String city;

    @NotBlank
    @JsonProperty("postalCode")
    private final String postalCode;

}
