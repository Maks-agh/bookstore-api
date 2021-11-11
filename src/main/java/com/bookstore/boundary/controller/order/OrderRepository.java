package com.bookstore.boundary.controller.order;

import com.bookstore.domain.order.OrderEntity;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    OrderEntity save(OrderEntity orderEntity);

    Optional<OrderEntity> findById(UUID id);

}
