package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.controller.api.request.CustomerJoinRequest;
import com.kdt.hairsalon.controller.api.request.UpdateCustomerRequest;
import com.kdt.hairsalon.service.customer.CustomerDto;
import com.kdt.hairsalon.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> join(@RequestBody @Valid CustomerJoinRequest request) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteById(@PathVariable("id") UUID id) {
        customerService.deleteById(id);

        return ResponseEntity.ok(id);
    }

    @PatchMapping
    public ResponseEntity<CustomerDto> updateComment(@RequestBody UpdateCustomerRequest request) {
        return ResponseEntity.ok(customerService.updateComment(request.getId(), request.getComment()));
    }
}
