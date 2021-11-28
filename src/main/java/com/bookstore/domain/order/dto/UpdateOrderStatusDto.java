package com.bookstore.domain.order.dto;

import com.bookstore.domain.order.OrderStatus;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class UpdateOrderStatusDto {

    private final UUID orderId;

    private final OrderStatus status;

}
