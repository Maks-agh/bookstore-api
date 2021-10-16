package com.bookstore;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2
@SpringBootApplication
public class BookstoreApi {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApi.class, args);
    }
}