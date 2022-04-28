package com.kdt.gccoffee.service;

import com.kdt.gccoffee.model.Category;
import com.kdt.gccoffee.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product create(String name, long price, Category category);

    Product findById(UUID id);

    Product findByName(String name);

    Product findByCategory(Category category);

    List<Product> findAll();
}
