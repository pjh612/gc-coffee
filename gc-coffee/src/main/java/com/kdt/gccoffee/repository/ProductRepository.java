package com.kdt.gccoffee.repository;

import com.kdt.gccoffee.model.Category;
import com.kdt.gccoffee.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product insert(Product product);

    Optional<Product> findById(UUID id);

    Optional<Product> findByName(String name);

    Optional<Product> findByCategory(Category category);

    List<Product> findAll();
}
