package com.bookstore.boundary.controller.customer;

import com.bookstore.domain.customer.dto.AddressDto;
import com.bookstore.domain.customer.dto.AuthorizationDto;
import com.bookstore.domain.customer.dto.CustomerLoginDto;
import com.bookstore.domain.customer.dto.CustomerRegistrationDto;
import com.bookstore.domain.customer.dto.UpdateCustomerDetailsDto;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerResourceFactory {

    static CustomerRegistrationDto toCustomerRegistrationDto(CustomerRegistrationDocument registrationDocument) {
        return new CustomerRegistrationDto(registrationDocument.getName(),
                registrationDocument.getEmail(),
                registrationDocument.getPhone(),
                registrationDocument.getPassword(),
                registrationDocument.getRepeatPassword(),
                toAddressDto(registrationDocument.getAddress()));
    }

    static AddressDto toAddressDto(AddressDocument addressDocument) {
        return new AddressDto(addressDocument.getFirstLine(),
                addressDocument.getSecondLine(),
                addressDocument.getCity(),
                addressDocument.getPostalCode()
        );
    }

    static CustomerLoginDto toCustomerLoginDto(CustomerLoginDocument loginDocument) {
        return new CustomerLoginDto(loginDocument.getEmail(), loginDocument.getPassword());
    }

    static AuthorizationDocument toAuthorizationDocument(AuthorizationDto authorizationDto) {
        return new AuthorizationDocument(authorizationDto.getToken());
    }

    static UpdateCustomerDetailsDto toUpdateCustomerDetailsDto(UpdateCustomerDetailsDocument updateDocument, UUID customerId) {
        return new UpdateCustomerDetailsDto(customerId,
                updateDocument.getName(),
                updateDocument.getEmail(),
                updateDocument.getPhone(),
                updateDocument.getPassword(),
                updateDocument.getRepeatPassword(),
                toAddressDto(updateDocument.getAddress()));
    }

}
