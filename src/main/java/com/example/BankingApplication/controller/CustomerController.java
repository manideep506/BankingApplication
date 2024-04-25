package com.example.BankingApplication.controller;

import com.example.BankingApplication.model.dto.CustomerRequest;
import com.example.BankingApplication.model.dto.CustomerResponse;
import com.example.BankingApplication.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        return status(HttpStatus.OK).body(customerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findByCurrentCustomer() {
        return status(HttpStatus.OK).body(customerService.findByCurrentCustomer());
    }

    @GetMapping("/all-with-card")
    public ResponseEntity<List<CustomerResponse>> findAllWithCard() {
        return status(HttpStatus.OK).body(customerService.findAllWithCard());
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<CustomerResponse>> findByLastname(@PathVariable String lastname) {
        return status(HttpStatus.OK).body(customerService.findByLastName(lastname));
    }

    //http://localhost:9090/api/customer/register
    @PostMapping("/register")
    public ResponseEntity<Void> saveCustomer(@RequestBody CustomerRequest customerRequest) {
        customerService.save(customerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
