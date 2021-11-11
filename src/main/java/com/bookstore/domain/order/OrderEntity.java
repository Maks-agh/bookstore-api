package com.bookstore.domain.order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Setter
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetailsEntity> orderDetails;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "received_at")
    private Instant receivedAt;

    @Column(name = "packed_at")
    private Instant packedAt;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    public OrderEntity(UUID customerId) {
        this.id = UUID.randomUUID();
        this.createdBy = customerId;
        this.receivedAt = Instant.now();
        this.packedAt = null;
        this.sentAt = null;
        this.status = OrderStatus.RECEIVED;
    }

}
