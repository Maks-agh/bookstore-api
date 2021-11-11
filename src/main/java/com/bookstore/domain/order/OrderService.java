package com.bookstore.domain.order;

import com.bookstore.boundary.controller.order.OrderRepository;
import com.bookstore.domain.order.dto.CreateOrderDto;
import com.bookstore.domain.product.ProductEntity;
import com.bookstore.domain.product.ProductService;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import static com.bookstore.domain.utils.StreamUtils.mapToList;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createOrder(CreateOrderDto orderDto) {
        log.info("Creating order {} started", orderDto);
        OrderEntity orderEntity = new OrderEntity(orderDto.getCustomerId());
        List<OrderDetailsEntity> orderDetails = createOrderDetails(orderDto, orderEntity);
        orderEntity.setOrderDetails(orderDetails);
        orderRepository.save(orderEntity);
        log.info("Creating order {} finished", orderEntity);
    }

    private List<OrderDetailsEntity> createOrderDetails(CreateOrderDto orderDto, OrderEntity orderEntity) {
        return mapToList(orderDto.getProducts(), product -> {
            ProductEntity productEntity = productService.findByIdAndInStockGreaterThanEqual(product.getProductId(),
                    product.getQuantity());
            productEntity.removeFromInStock(product.getQuantity());
            Double price = product.getQuantity() * productEntity.getPrice();
            return new OrderDetailsEntity(orderEntity, productEntity, product.getQuantity(), price);
        });
    }

}
