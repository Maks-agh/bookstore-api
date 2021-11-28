package com.bookstore.domain.order.dto;

import com.bookstore.domain.customer.dto.AddressDto;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CreateOrderDto {

    private final List<CreateOrderProductDto> products;

    private final CustomerDto customer;

    @Getter
    @ToString
    @Builder
    public static class CustomerDto {

        private UUID id;

        private boolean isAuthenticated;

        private String name;

        private String email;

        private String phone;

        private AddressDto address;
    }
}