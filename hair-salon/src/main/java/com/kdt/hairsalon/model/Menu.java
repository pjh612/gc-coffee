package com.kdt.hairsalon.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Menu {
    private final UUID id;
    private final String name;
    private int price;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Menu(UUID id, String name, int price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt.withNano(0);
        this.updatedAt = updatedAt.withNano(0);
    }
}
