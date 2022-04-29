package com.kdt.gccoffee.service;

import com.kdt.gccoffee.model.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDto create(String name, String email, Gender gender, LocalDate birth);

    CustomerDto findById(UUID id);

    List<CustomerDto> findByName(String name);

    CustomerDto findByEmail(String email);

    List<CustomerDto> findAll();
}
