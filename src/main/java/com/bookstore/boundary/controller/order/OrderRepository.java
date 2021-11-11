package com.bookstore.boundary.controller.order;

import com.bookstore.domain.order.OrderEntity;

public interface OrderRepository {

    OrderEntity save(OrderEntity orderEntity);

}
