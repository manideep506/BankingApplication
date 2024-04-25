package com.example.BankingApplication.service;

import com.example.BankingApplication.exceptions.BankCustomerException;
import com.example.BankingApplication.mapper.CustomerMapper;
import com.example.BankingApplication.model.Customer;
import com.example.BankingApplication.model.dto.CustomerRequest;
import com.example.BankingApplication.model.dto.CustomerResponse;
import com.example.BankingApplication.repo.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

//    returns data about current customer
    public CustomerResponse findById(Long id) {
        Customer currentCustomer = getCurrentCustomer();
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new BankCustomerException("Customer not found"));
        if(customer.getEmail().equals(currentCustomer.getEmail()))
            return customerMapper.mapToDto(customer);
        throw new BankCustomerException("Unauthorized!");
    }

    @Transactional(readOnly = true)
    public Customer getCurrentCustomer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepo.findByEmail(email).orElseThrow(() -> new BankCustomerException("Customer not found"));
    }

    public List<CustomerResponse> findAllWithCard() {
        return customerRepo.findAllWithCard().stream().map(customerMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CustomerResponse> findByLastName(String lastname) {
        return customerRepo.findByLastname(lastname).stream().map(customerMapper::mapToDto).collect(Collectors.toList());
    }

//    saves customer account in database
    public void save(CustomerRequest customerRequest) {
        customerRequest.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        Customer customer = customerMapper.map(customerRequest);
        enableAccount(customer);
        customerRepo.save(customer);
    }

//    sets account to enabled
    private void enableAccount(Customer customer) {
        customer.setEnabled(true);
    }

    public CustomerResponse findByCurrentCustomer() {
        Customer currentCustomer = getCurrentCustomer();
        return customerMapper.mapToDto(currentCustomer);
    }

    public Customer findByAccountNumber(String recipientAccountNumber) {
        return customerRepo.findByBankAccountNumber(recipientAccountNumber).orElseThrow(() -> new BankCustomerException("Recipient not found"));
    }

    @Transactional
    public void editCustomerBalance(Customer customer) {
        customerRepo.save(customer);
    }

    public List<Customer> findAllById(Set<Long> customerIds) {
        return customerRepo.findAllById(customerIds);
    }
}
