package com.bookstore.boundary.controller.order;

import com.bookstore.domain.order.OrderStatus;
import com.bookstore.domain.order.dto.CreateOrderDto;
import com.bookstore.domain.order.dto.CreateOrderProductDto;
import com.bookstore.domain.order.dto.UpdateOrderStatusDto;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import static com.bookstore.domain.utils.StreamUtils.mapToList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResourceFactory {

    static CreateOrderDto toCreateOrderDto(CreateOrderDocument createOrderDocument, UUID customerId) {
        return new CreateOrderDto(toCreateOrderProductsDto(createOrderDocument.getProducts()), customerId);
    }

    static UpdateOrderStatusDto toUpdateOrderStatusDto(UUID orderId, UpdateOrderStatusDocument orderStatusDocument) {
        return new UpdateOrderStatusDto(orderId, OrderStatus.valueOf(orderStatusDocument.getStatus()));
    }

    private static List<CreateOrderProductDto> toCreateOrderProductsDto(List<CreateOrderProductDocument> productsDocument) {
        return mapToList(productsDocument, OrderResourceFactory::toCreateOrderProductDto);
    }

    private static CreateOrderProductDto toCreateOrderProductDto(CreateOrderProductDocument productDocument) {
        return new CreateOrderProductDto(productDocument.getProductId(), productDocument.getQuantity());
    }

}
