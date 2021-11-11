package com.bookstore.boundary.controller.order;

import com.bookstore.domain.exception.ValidationException;
import com.bookstore.domain.order.OrderService;
import javax.validation.Valid;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


import static com.bookstore.boundary.controller.order.OrderResourceFactory.toCreateOrderDto;
import static com.bookstore.boundary.controller.order.OrderResourceFactory.toOrdersListDocument;
import static com.bookstore.boundary.controller.order.OrderResourceFactory.toUpdateOrderStatusDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "orders")
class OrdersController {

    private final OrderService orderService;

    @PostMapping
    void createOrder(UsernamePasswordAuthenticationToken principal,
                     @RequestBody @Valid CreateOrderDocument createOrderDocument) {
        UUID customerId = extractCustomerId(principal, createOrderDocument);
        orderService.createOrder(toCreateOrderDto(createOrderDocument, customerId));
    }

    @PatchMapping("/{order-id}")
    void updateOrderStatus(@PathVariable("order-id") UUID orderId,
                           @RequestBody @Valid UpdateOrderStatusDocument orderStatusDocument) {
        orderService.updateOrderStatus(toUpdateOrderStatusDto(orderId, orderStatusDocument));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    OrdersListDocument getOwnOrdersList(UsernamePasswordAuthenticationToken principal) {
        UUID customerId = UUID.fromString(principal.getName());
        return toOrdersListDocument(orderService.getOrdersList(customerId));
    }

    private UUID extractCustomerId(UsernamePasswordAuthenticationToken principal,
                                   CreateOrderDocument createOrderDocument) {
        if (principal != null && principal.getName() != null && !principal.getName().isEmpty()) {
            return UUID.fromString(principal.getName());
        }
        if (createOrderDocument.getCustomerId() != null) {
            return createOrderDocument.getCustomerId();
        }
        throw new ValidationException("Customer id must be provided");
    }

}
