package com.bookstore.domain.customer


import com.bookstore.domain.address.AddressEntity
import com.bookstore.domain.customer.dto.AddressDto
import com.bookstore.domain.customer.dto.CustomerLoginDto
import com.bookstore.domain.customer.dto.CustomerRegistrationDto
import com.bookstore.domain.password.PasswordService
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import spock.lang.Specification

class CustomerServiceSpec extends Specification {

    CustomerRepository customerRepository = Mock(CustomerRepository)
    PasswordService passwordService = Mock(PasswordService)
    MeterRegistry meterRegistry = new SimpleMeterRegistry()

    CustomerService customerService = new CustomerService(customerRepository, passwordService, meterRegistry)

    def "should register customer"() {
        given:
            def registrationDto = new CustomerRegistrationDto("name", "email", "123456789", "password",
                    "password", new AddressDto("firstLine", "secondLine", "city", "00-000"))
        when:
            def authorizationDto = customerService.register(registrationDto)
        then:
            1 * customerRepository.save(_) >> { CustomerEntity customerEntity ->
                customerEntity.name == "name"
                customerEntity.email == "email"
                customerEntity.phone == "123456789"
                customerEntity.password == "password"
                customerEntity.address.firstLine == "firstLine"
                customerEntity.address.secondLine == "secondLine"
                customerEntity.address.city == "city"
                customerEntity.address.postalCode == "00-000"
                customerEntity
            }
            authorizationDto
    }

    def "should log in customer"() {
        given:
            def loginDto = new CustomerLoginDto("email", "password")
        when:
            def authorizationDto = customerService.login(loginDto)
        then:
            1 * passwordService.isPasswordMatching(_, _) >> true
            1 * customerRepository.findByEmail(loginDto.getEmail()) >> Optional.of(new CustomerEntity("name", "email", "123456789", "password",
                    new AddressEntity("firstLine", "secondLine", "city", "00-000")))
            authorizationDto
    }
}
