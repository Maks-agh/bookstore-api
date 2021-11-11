package com.bookstore.boundary.controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateOrderStatusDocument {

    @NotBlank
    @JsonProperty("status")
    private String status;

}
