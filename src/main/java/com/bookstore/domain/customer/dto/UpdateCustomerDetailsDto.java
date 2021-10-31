package com.bookstore.domain.customer.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class UpdateCustomerDetailsDto {

    private final UUID customerId;

    private final String name;

    private final String email;

    private final String phone;

    private final String password;

    private final String repeatPassword;

    private final AddressDto address;
}
