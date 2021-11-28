package com.bookstore.domain.product


import com.bookstore.domain.product.dto.CreateProductDto
import com.bookstore.domain.product.dto.UpdateProductDto
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import spock.lang.Specification

class ProductServiceSpec extends Specification {

    ProductRepository productRepository = Mock(ProductRepository)
    MeterRegistry meterRegistry = new SimpleMeterRegistry()

    ProductService productService = new ProductService(productRepository, meterRegistry)

    def "should create product"() {
        given:
            def productId = UUID.randomUUID()
            def createProductDto = new CreateProductDto(productId, "name", "description", 1, 1.0)
        when:
            productService.createProduct(createProductDto)
        then:
            1 * productRepository.save(_) >> { ProductEntity productEntity ->
                productEntity.id == productId
                productEntity.name == "name"
                productEntity.description == "description"
                productEntity.inStock == 1
                productEntity.price == 1.0
                productEntity
            }
    }

    def "should delete product"() {
        given:
            def productId = UUID.randomUUID()
        when:
            productService.deleteProduct(productId)
        then:
            1 * productRepository.deleteById(productId)
            1 * productRepository.findById(productId) >> Optional.of(new ProductEntity(productId, "name", "email", 1, 1.0))
    }

    def "should update product"() {
        given:
            def productId = UUID.randomUUID()
            def updateProductDto = new UpdateProductDto(productId, "name2", "description2", 2, 2.0)
        when:
            productService.updateProduct(updateProductDto)
        then:
            1 * productRepository.findById(productId) >> Optional.of(new ProductEntity(productId, "name", "email", 1, 1.0))
            1 * productRepository.save(_) >> { ProductEntity productEntity ->
                productEntity.id == productId
                productEntity.name == "name2"
                productEntity.description == "description2"
                productEntity.inStock == 2
                productEntity.price == 2.0
                productEntity
            }
    }
}
