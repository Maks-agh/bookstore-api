package com.bookstore.domain.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CustomerRegistrationDto {

    private final String name;

    private final String email;

    private final String phone;

    private final String password;

    private final String repeatPassword;

    private final AddressDto address;
}
