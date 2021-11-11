package com.bookstore.infrastructure.postgres.order;

import com.bookstore.boundary.controller.order.OrderRepository;
import com.bookstore.domain.order.OrderEntity;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPostgresRepository extends OrderRepository, JpaRepository<OrderEntity, UUID> {

    OrderEntity save(OrderEntity orderEntity);

}
