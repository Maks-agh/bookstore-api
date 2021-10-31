package com.bookstore.domain.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorizationDto {

    private final String token;
}
