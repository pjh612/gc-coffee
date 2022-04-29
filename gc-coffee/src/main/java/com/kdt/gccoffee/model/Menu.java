package com.kdt.gccoffee.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final String name;
    private int price;
    private final LocalDateTime createdAt;

    public Menu(UUID id, String name, int price, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
