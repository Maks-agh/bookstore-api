package com.bookstore.domain.report;

import com.bookstore.domain.address.AddressEntity;
import com.bookstore.domain.order.OrderStatus;
import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ReportProjection {

    private final String customerName;

    private final AddressEntity customerAddress;

    private final Instant receivedAt;

    private final Instant packedAt;

    private final Instant sentAt;

    private final OrderStatus status;

    private final UUID orderId;

}
