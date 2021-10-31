package com.bookstore.domain.product;

import com.bookstore.domain.product.dto.UpdateProductDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private Double inStock;

    @Column(name = "price")
    private Double price;

    public ProductEntity(String name, String description, Double inStock, Double price) {
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
}
