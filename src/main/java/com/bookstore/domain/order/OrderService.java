package com.bookstore.domain.order;

import com.bookstore.domain.customer.CustomerService;
import com.bookstore.domain.exception.NotFoundException;
import com.bookstore.domain.order.dto.CreateOrderDto;
import com.bookstore.domain.order.dto.OrderDetailDto;
import com.bookstore.domain.order.dto.OrderDto;
import com.bookstore.domain.order.dto.UpdateOrderStatusDto;
import com.bookstore.domain.product.ProductEntity;
import com.bookstore.domain.product.ProductService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private final CustomerService customerService;

    private final MeterRegistry meterRegistry;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createOrder(CreateOrderDto orderDto) {
        log.info("Creating order {} started", orderDto);
        OrderEntity orderEntity = new OrderEntity(orderDto.getCustomer().getId());
        List<OrderDetailsEntity> orderDetails = createOrderDetails(orderDto, orderEntity);
        orderEntity.setOrderDetails(orderDetails);
        orderRepository.save(orderEntity);
        if (!orderDto.getCustomer().isAuthenticated()) {
            customerService.createUnregisteredForOrder(orderDto.getCustomer());
        }
        recordOrderCreation(orderDetails.stream().mapToDouble(OrderDetailsEntity::getPrice).sum());
        log.info("Creating order {} finished", orderEntity.getId());
    }

    public void updateOrderStatus(UpdateOrderStatusDto orderStatusDto) {
        log.info("Updating order status {} started", orderStatusDto);
        OrderEntity orderEntity = findOrderById(orderStatusDto.getOrderId());
        orderEntity.updateStatus(orderStatusDto.getStatus());
        orderRepository.save(orderEntity);
        log.info("Updating order status {} finished", orderEntity.getId());
    }

    public List<OrderDto> getOrdersList(UUID customerId) {
        log.info("Fetching orders list for customer {} started", customerId);
        List<OrderEntity> orders = orderRepository.findByCreatedBy(customerId);
        log.info("Fetching orders list for customer {} finished", customerId);
        return mapToList(orders, this::buildOrderDto);
    }

    public List<OrderDto> getUnregisteredOrdersList(UUID customerId) {
        log.info("Fetching orders list for unregistered customer {} started", customerId);
        if (customerService.unregisteredCustomerExists(customerId)) {
            throw new NotFoundException("Unregistered customer doesn't exist");
        }
        List<OrderEntity> orders = orderRepository.findByCreatedBy(customerId);
        log.info("Fetching orders list for unregistered customer {} finished", customerId);
        return mapToList(orders, this::buildOrderDto);
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

    private OrderEntity findOrderById(UUID orderId) {
        return findOptionalOrderById(orderId).orElseThrow(() -> new NotFoundException("Order doesn't exist"));
    }

    private Optional<OrderEntity> findOptionalOrderById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    private OrderDto buildOrderDto(OrderEntity orderEntity) {
        return new OrderDto(orderEntity.getId(), mapToList(orderEntity.getOrderDetails(), this::buildOrderDetailDto), orderEntity.getReceivedAt(),
                orderEntity.getPackedAt(),
                orderEntity.getSentAt(), orderEntity.getStatus());
    }

    private OrderDetailDto buildOrderDetailDto(OrderDetailsEntity orderDetail) {
        return new OrderDetailDto(orderDetail.getProduct().getId(), orderDetail.getProduct().getName(), orderDetail.getQuantity(),
                orderDetail.getPrice());
    }

    private void recordOrderCreation(Double price) {
        Counter orderCounter = Counter
                .builder("order_create")
                .description("indicates number of orders created")
                .register(meterRegistry);
        Counter priceCounter = Counter
                .builder("order_price")
                .description("indicates total price of created orders")
                .register(meterRegistry);
        orderCounter.increment();
        priceCounter.increment(price);
    }
}
