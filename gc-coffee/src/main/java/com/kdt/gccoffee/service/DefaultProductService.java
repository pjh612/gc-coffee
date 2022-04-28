package com.kdt.gccoffee.service;

import com.kdt.gccoffee.model.Category;
import com.kdt.gccoffee.model.Product;
import com.kdt.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(String name, long price, Category category) {
        Product product = new Product(UUID.randomUUID(), name, category, price, LocalDateTime.now(), LocalDateTime.now());

        return productRepository.insert(product);
    }

    @Override
    @Transactional
    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("제품 정보를 찾을 수 없습니다."));

    }

    @Override
    @Transactional
    public Product findByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("제품 정보를 찾을 수 없습니다."));

    }

    @Override
    @Transactional
    public Product findByCategory(Category category) {
        return productRepository.findByCategory(category).orElseThrow(() -> new IllegalArgumentException("제품 정보를 찾을 수 없습니다."));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
