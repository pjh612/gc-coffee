package com.kdt.gccoffee.controller.api;

public class CreateMenuRequest {
    private final String name;

    private final int price;

    public CreateMenuRequest(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
