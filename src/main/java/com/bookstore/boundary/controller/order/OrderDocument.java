package com.bookstore.boundary.controller.order;

import com.bookstore.domain.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDocument {

    @JsonProperty("orderId")
    private final UUID orderId;

    @JsonProperty("orderDetails")
    private final List<OrderDetailDocument> orderDetails;

    @JsonProperty("receivedAt")
    private final Instant receivedAt;

    @JsonProperty("packedAt")
    private final Instant packedAt;

    @JsonProperty("sentAt")
    private final Instant sentAt;

    @JsonProperty("status")
    private final OrderStatus status;
}
