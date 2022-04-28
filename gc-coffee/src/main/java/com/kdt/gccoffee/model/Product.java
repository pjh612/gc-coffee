package com.kdt.gccoffee.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    UUID id;
    String name;
    Category category;
    long price;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Product(UUID id, String name, Category category, long price, LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
