package com.bookstore.domain.auth;

import org.springframework.security.core.GrantedAuthority;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerAuthority implements GrantedAuthority {

    private final String customerId;

    @Override
    public String getAuthority() {
        return customerId;
    }
}
