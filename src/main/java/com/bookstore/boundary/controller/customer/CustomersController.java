package com.bookstore.boundary.controller.customer;

import com.bookstore.domain.customer.CustomerService;
import com.bookstore.domain.customer.dto.CustomerDto;
import javax.validation.Valid;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


import static com.bookstore.boundary.controller.customer.CustomerResourceFactory.toAuthorizationDocument;
import static com.bookstore.boundary.controller.customer.CustomerResourceFactory.toCustomerLoginDto;
import static com.bookstore.boundary.controller.customer.CustomerResourceFactory.toCustomerRegistrationDto;
import static com.bookstore.boundary.controller.customer.CustomerResourceFactory.toUpdateCustomerDetailsDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "customers")
class CustomersController {

    private final CustomerService customerService;

    @PostMapping("/registration")
    AuthorizationDocument register(@RequestBody @Valid CustomerRegistrationDocument registrationDocument) {
        return toAuthorizationDocument(customerService.register(toCustomerRegistrationDto(registrationDocument)));
    }

    @PostMapping("/login")
    AuthorizationDocument login(

            @RequestBody @Valid CustomerLoginDocument loginDocument) {
        return toAuthorizationDocument(customerService.login(toCustomerLoginDto(loginDocument)));
    }

    @GetMapping("/{customer-id}")
    CustomerDto getCustomer(@PathVariable("customer-id") UUID customerId) {
        return customerService.getCustomer(customerId);
    }

    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    void updateDetails(UsernamePasswordAuthenticationToken principal,
                       @RequestBody @Valid UpdateCustomerDetailsDocument updateCustomerDetailsDocument) {
        UUID customerId = UUID.fromString(principal.getName());
        customerService.updateDetails(toUpdateCustomerDetailsDto(updateCustomerDetailsDocument, customerId));
    }

}
