package com.bookstore.domain.customer;

import com.bookstore.domain.address.AddressEntity;
import com.bookstore.domain.customer.dto.UpdateCustomerDetailsDto;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "customers")
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    CustomerEntity(String name, String email, String phone, String password, AddressEntity address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
    }

    private CustomerEntity(String name, String email, String phone, AddressEntity address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    static CustomerEntity createUnregistered(String name, String email, String phone, AddressEntity address) {
        return new CustomerEntity(name, email, phone, address);
    }

    void updateCustomer(UpdateCustomerDetailsDto updateDto) {
        this.name = updateDto.getName() != null ? updateDto.getName() : this.name;
        this.email = updateDto.getEmail() != null ? updateDto.getEmail() : this.email;
        this.phone = updateDto.getPhone() != null ? updateDto.getPhone() : this.phone;
        this.password = updateDto.getPassword() != null ? updateDto.getPassword() : this.password;
        this.address = updateDto.getAddress() != null ? this.address.update(updateDto.getAddress()) : this.address;
    }
}
