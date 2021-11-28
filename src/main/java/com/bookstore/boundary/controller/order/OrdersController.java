package com.bookstore.boundary.controller.order;

import com.bookstore.boundary.controller.order.CreateOrderDocument.CustomerDocument;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


import static com.bookstore.boundary.controller.order.OrderResourceFactory.toCreateOrderDto;
import static com.bookstore.boundary.controller.order.OrderResourceFactory.toOrdersListDocument;
import static com.bookstore.boundary.controller.order.OrderResourceFactory.toUpdateOrderStatusDto;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "orders")
class OrdersController {

    private final OrderService orderService;

    @PostMapping
    void createOrder(UsernamePasswordAuthenticationToken principal,
                     @RequestBody @Valid CreateOrderDocument createOrderDocument) {
        validate(principal, createOrderDocument);
        UUID customerId = extractCustomerId(principal, createOrderDocument);
        orderService.createOrder(toCreateOrderDto(createOrderDocument, createOrderDocument.getCustomer(), customerId, isAuthenticatedCustomer(principal)));
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

    @GetMapping("unregistered")
    OrdersListDocument getUnregistredOrdersList(@RequestParam UUID customerId) {
        return toOrdersListDocument(orderService.getUnregisteredOrdersList(customerId));
    }

    private void validate(UsernamePasswordAuthenticationToken principal,
                          CreateOrderDocument order) {
        if (isAuthenticatedCustomer(principal)) {
            return;
        }
        if (isNull(order.getCustomer())) {
            throw new ValidationException("Incorrect order data");
        }
    }

    private UUID extractCustomerId(UsernamePasswordAuthenticationToken principal,
                                   CreateOrderDocument createOrderDocument) {
        if (isAuthenticatedCustomer(principal)) {
            return UUID.fromString(principal.getName());
        }
        CustomerDocument customer = createOrderDocument.getCustomer();
        if (customer.getId() != null) {
            return customer.getId();
        }
        throw new ValidationException("Customer id must be provided");
    }

    private boolean isAuthenticatedCustomer(UsernamePasswordAuthenticationToken principal) {
        return !isNull(principal) && isNotBlank(principal.getName());
    }

}
