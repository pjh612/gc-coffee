package com.kdt.gccoffee.repository.customer;

import com.kdt.gccoffee.model.Category;
import com.kdt.gccoffee.model.Customer;
import com.kdt.gccoffee.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Optional<Customer> findById(UUID id);

    List<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();
}
