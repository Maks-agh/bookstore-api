package com.bookstore.domain.order;

import com.bookstore.domain.product.ProductEntity;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_details")
@NoArgsConstructor
public class OrderDetailsEntity {

    @EmbeddedId
    private OrderDetailsId orderDetailsId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public OrderDetailsEntity(OrderEntity orderEntity, ProductEntity productEntity, Integer quantity, Double price) {
        this.orderDetailsId = new OrderDetailsId(orderEntity.getId(), productEntity.getId());
        this.quantity = quantity;
        this.price = price;
        this.order = orderEntity;
        this.product = productEntity;
    }

}
