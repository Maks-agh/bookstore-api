package com.bookstore.domain.customer.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerDto {

    private final UUID id;

    private final String name;

    private final String email;

    private final String phone;

    private final AddressDto address;

}
