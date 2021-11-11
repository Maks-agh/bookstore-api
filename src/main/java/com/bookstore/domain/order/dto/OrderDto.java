package com.bookstore.domain.order.dto;

import com.bookstore.domain.order.OrderStatus;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class OrderDto {

    private final UUID orderId;

    private final List<OrderDetailDto> orderDetails;

    private final Instant receivedAt;

    private final Instant packedAt;

    private final Instant sentAt;

    private final OrderStatus status;

}
