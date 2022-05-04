package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.service.customer.CustomerDto;
import com.kdt.hairsalon.service.customer.CustomerService;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> join(@RequestBody CustomerJoinRequest request) {
        return ResponseEntity.ok(customerService.create(request.getName(), request.getEmail(), request.getGender(), request.getBirth()));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerDto> getByCustomerId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDto> getByCustomerId(@PathVariable("email") String email) {
        return ResponseEntity.ok(customerService.findByEmail(email));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<CustomerDto>> getByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(customerService.findByName(name));
    }
}
