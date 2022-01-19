package com.bookstore.domain.address;

import com.bookstore.domain.customer.CustomerEntity;
import com.bookstore.domain.customer.dto.AddressDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "addresses")
@NoArgsConstructor
@ToString(exclude = {"id", "customer"})
public class AddressEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_line")
    private String firstLine;

    @Column(name = "second_line")
    private String secondLine;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @OneToOne(mappedBy = "address")
    private CustomerEntity customer;

    private AddressEntity(String firstLine, String secondLine, String city, String postalCode) {
        this.id = UUID.randomUUID();
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.city = city;
        this.postalCode = postalCode;
    }

    public static AddressEntity of(AddressDto addressDto) {
        return new AddressEntity(addressDto.getFirstLine(), addressDto.getSecondLine(), addressDto.getCity(), addressDto.getPostalCode());
    }

    public AddressEntity update(AddressDto addressDto) {
        this.firstLine = addressDto != null ? addressDto.getFirstLine() : firstLine;
        this.secondLine = addressDto != null ? addressDto.getSecondLine() : secondLine;
        this.city = addressDto != null ? addressDto.getCity() : city;
        this.postalCode = addressDto != null ? addressDto.getPostalCode() : postalCode;
        return this;
    }

}
