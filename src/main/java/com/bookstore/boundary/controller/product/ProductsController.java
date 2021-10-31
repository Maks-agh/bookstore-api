package com.bookstore.boundary.controller.product;

import com.bookstore.domain.product.ProductService;
import javax.validation.Valid;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


import static com.bookstore.boundary.controller.product.ProductResourceFactory.toProductDto;
import static com.bookstore.boundary.controller.product.ProductResourceFactory.toUpdateProductDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "products")
class ProductsController {

    private final ProductService productService;

    @PostMapping
    void createProduct(@RequestBody @Valid CreateProductDocument productDocument) {
        productService.createProduct(toProductDto(productDocument));
    }

    @DeleteMapping("/{product-id}")
    void deleteProduct(@PathVariable("product-id") UUID productId) {
        productService.deleteProduct(productId);
    }

    @PatchMapping("/{product-id}")
    void updateProduct(@PathVariable("product-id") UUID productId,
                       @RequestBody @Valid UpdateProductDocument productDocument) {
        productService.updateProduct(toUpdateProductDto(productId, productDocument));
    }

}
