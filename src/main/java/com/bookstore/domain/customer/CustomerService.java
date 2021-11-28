package com.bookstore.domain.customer;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bookstore.domain.address.AddressEntity;
import com.bookstore.domain.customer.dto.AddressDto;
import com.bookstore.domain.customer.dto.AuthorizationDto;
import com.bookstore.domain.customer.dto.CustomerDto;
import com.bookstore.domain.customer.dto.CustomerLoginDto;
import com.bookstore.domain.customer.dto.CustomerRegistrationDto;
import com.bookstore.domain.customer.dto.UpdateCustomerDetailsDto;
import com.bookstore.domain.exception.AuthorizationException;
import com.bookstore.domain.exception.NotFoundException;
import com.bookstore.domain.exception.ValidationException;
import com.bookstore.domain.order.dto.CreateOrderDto;
import com.bookstore.domain.password.PasswordService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import static com.bookstore.domain.auth.AuthorizationUtils.CUSTOMER_ID;
import static com.bookstore.domain.auth.AuthorizationUtils.JWT_SECRET;
import static java.time.temporal.ChronoUnit.HOURS;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordService passwordService;

    private final MeterRegistry meterRegistry;

    public AuthorizationDto register(CustomerRegistrationDto registrationDto) {
        log.info("Registering customer {} started", registrationDto);
        validateRegistration(registrationDto);
        CustomerEntity customer = saveCustomer(registrationDto);
        String token = generateJwt(customer);
        log.info("Registering customer {} finished", customer.getId());
        recordRegistration();
        return new AuthorizationDto(token);
    }

    public AuthorizationDto login(CustomerLoginDto loginDto) {
        CustomerEntity customer = findCustomer(loginDto);
        validateLogin(customer, loginDto);
        String token = generateJwt(customer);
        return new AuthorizationDto(token);
    }

    public CustomerDto getCustomer(UUID customerId) {
        CustomerEntity customer = findCustomerById(customerId);
        return new CustomerDto(customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                getAddress(customer.getAddress()));
    }

    public boolean unregisteredCustomerExists(UUID customerId) {
        return findUnregisteredCustomerById(customerId).isPresent();
    }

    public void updateDetails(UpdateCustomerDetailsDto updateDto) {
        log.info("Update'ing customer details {} started", updateDto);
        CustomerEntity customer = findCustomerById(updateDto.getCustomerId());
        customer.updateCustomer(updateDto);

        customerRepository.save(customer);
        log.info("Update'ing customer details {} finished", customer.getId());
    }

    public void createUnregisteredForOrder(CreateOrderDto.CustomerDto customerDto) {
        log.info("Creating unregistered customer {} for order started", customerDto.getId());
        CustomerEntity customer = CustomerEntity.createUnregistered(customerDto.getName(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                AddressEntity.of(customerDto.getAddress()));
        customerRepository.save(customer);
        log.info("Creating unregistered customer {} for order finished", customer.getId());
    }

    private AddressDto getAddress(AddressEntity addressEntity) {
        return new AddressDto(addressEntity.getFirstLine(),
                addressEntity.getSecondLine(),
                addressEntity.getCity(),
                addressEntity.getPostalCode());
    }

    private void validateRegistration(CustomerRegistrationDto registrationDto) {
        if (!registrationDto.getPassword().equals(registrationDto.getRepeatPassword())) {
            throw new ValidationException("Passwords are not the same");
        }
    }

    private void validateLogin(CustomerEntity customerEntity, CustomerLoginDto loginDto) {
        if (!passwordService.isPasswordMatching(loginDto.getPassword(), customerEntity.getPassword())) {
            throw new AuthorizationException("Customer provided incorrect password");
        }
    }

    private CustomerEntity findCustomer(CustomerLoginDto loginDto) {
        return customerRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new NotFoundException("Customer doesn't exist"));
    }

    private CustomerEntity findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer doesn't exist"));
    }

    private Optional<CustomerEntity> findUnregisteredCustomerById(UUID customerId) {
        return customerRepository.findUnregisteredById(customerId);
    }

    private CustomerEntity saveCustomer(CustomerRegistrationDto registrationDto) {
        String encodedPassword = passwordService.encodePassword(registrationDto.getPassword());
        CustomerEntity customer = new CustomerEntity(registrationDto.getName(),
                registrationDto.getEmail(),
                registrationDto.getPhone(),
                encodedPassword,
                AddressEntity.of(registrationDto.getAddress()));
        return customerRepository.save(customer);
    }

    private String generateJwt(CustomerEntity customerEntity) {
        return JWT.create()
                .withClaim(CUSTOMER_ID, customerEntity.getId().toString())
                .withExpiresAt(Date.from(Instant.now().plus(24, HOURS)))
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    private void recordRegistration() {
        Counter counter = Counter
                .builder("customer_registration")
                .description("indicates number of customers registrations")
                .register(meterRegistry);
        counter.increment();
    }
}
