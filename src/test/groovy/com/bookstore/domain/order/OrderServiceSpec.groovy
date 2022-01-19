package com.bookstore.domain.order


import com.bookstore.domain.customer.CustomerService
import com.bookstore.domain.order.dto.CreateOrderDto
import com.bookstore.domain.order.dto.CreateOrderProductDto
import com.bookstore.domain.order.dto.UpdateOrderStatusDto
import com.bookstore.domain.product.ProductEntity
import com.bookstore.domain.product.ProductService
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import spock.lang.Specification

class OrderServiceSpec extends Specification {

    OrderRepository orderRepository = Mock(OrderRepository)
    ProductService productService = Mock(ProductService)
    CustomerService customerService = Mock(CustomerService)
    MeterRegistry meterRegistry = new SimpleMeterRegistry()

    OrderService orderService = new OrderService(orderRepository, productService, customerService, meterRegistry)

    def "should create order"() {
        given:
            def customerId = UUID.randomUUID()
            def productId = UUID.randomUUID()
            def customer = new CreateOrderDto.CustomerDto(customerId, true, null, null, null, null)
            def createOrderDto = new CreateOrderDto([new CreateOrderProductDto(productId, 1)], customer)
        when:
            orderService.createOrder(createOrderDto)
        then:
            1 * productService.findByIdAndInStockGreaterThanEqual(_, _) >> new ProductEntity(productId, "name", "description", 1, 1.0)
            1 * orderRepository.save(_) >> { OrderEntity orderEntity ->
                orderEntity.createdBy == customerId
                orderEntity.orderDetails.size() == 1
                orderEntity.orderDetails[0].product.id == productId
                orderEntity.orderDetails[0].quantity == 1
                orderEntity.receivedAt
                orderEntity.status == OrderStatus.RECEIVED
                orderEntity
            }
    }

    def "should update order status"() {
        given:
            def customerId = UUID.randomUUID()
            def orderId = UUID.randomUUID()
            def updateOrderStatusDto = new UpdateOrderStatusDto(orderId, OrderStatus.PACKED)
        when:
            orderService.updateOrderStatus(updateOrderStatusDto)
        then:
            1 * orderRepository.findById(orderId) >> Optional.of(new OrderEntity(customerId))
            1 * orderRepository.save(_) >> { OrderEntity orderEntity ->
                orderEntity.status == OrderStatus.PACKED
                orderEntity.packedAt
                orderEntity
            }
    }

    def "should get orders list"() {
        given:
            def customerId = UUID.randomUUID()
        when:
            def orders = orderService.getOrdersList(customerId)
        then:
            1 * orderRepository.findByCreatedBy(customerId) >> [new OrderEntity(customerId)]
            orders.size() == 1
            orders[0].orderId
            orders[0].status == OrderStatus.RECEIVED
    }
}
