package com.kdt.gccoffee.service.customer;

import com.kdt.gccoffee.model.Customer;
import com.kdt.gccoffee.model.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {
    private final UUID id;
    private final String name;
    private final String email;
    private final Gender gender;
    private final LocalDate birth;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CustomerDto(UUID id, String name, String email, Gender gender, LocalDate birth,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CustomerDto of(Customer customer) {
        return new CustomerDto(customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getGender(),
                customer.getBirth(),
                customer.getCreatedAt(),
                customer.getUpdatedAt());
    }
}
