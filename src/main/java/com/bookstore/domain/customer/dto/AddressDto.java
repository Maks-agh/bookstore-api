package com.bookstore.domain.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class AddressDto {

    private final String firstLine;

    private final String secondLine;

    private final String city;

    private final String postalCode;
}
