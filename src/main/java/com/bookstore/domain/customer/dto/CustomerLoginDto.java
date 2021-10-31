package com.bookstore.domain.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerLoginDto {

    private final String email;

    private final String password;
}
