package com.kdt.hairsalon.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Customer {
    private final UUID id;
    private final String name;
    private String email;
    private final Gender gender;
    private final LocalDate birth;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Customer(UUID id, String name, String email, Gender gender, LocalDate birth, LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.createdAt = createdAt.withNano(0);
        this.updatedAt = updatedAt.withNano(0);
    }
}
