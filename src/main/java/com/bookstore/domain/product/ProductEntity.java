package com.bookstore.domain.product;

import com.bookstore.domain.exception.ValidationException;
import com.bookstore.domain.order.OrderDetailsEntity;
import com.bookstore.domain.product.dto.UpdateProductDto;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "in_stock")
    private Integer inStock;

    @Column(name = "price")
    private Double price;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetailsEntity> orderDetail;

    public ProductEntity(String name, String description, Integer inStock, Double price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.inStock = inStock;
        this.price = price;
    }

    void updateEntity(UpdateProductDto updateDto) {
        this.name = updateDto.getName() != null ? updateDto.getName() : this.name;
        this.description = updateDto.getDescription() != null ? updateDto.getDescription() : this.description;
        this.inStock = updateDto.getInStock() != null ? updateDto.getInStock() : this.inStock;
        this.price = updateDto.getPrice() != null ? updateDto.getPrice() : this.price;
    }

    public void removeFromInStock(Integer inStock) {
        int newInStock = this.inStock - inStock;
        if (newInStock < 0) {
            throw new ValidationException("New inStock value must be greater or equal 0");
        }
        this.inStock = newInStock;
    }

}
