package com.example.BankingApplication.mapper;

import com.example.BankingApplication.model.Customer;
import com.example.BankingApplication.model.dto.CustomerRequest;
import com.example.BankingApplication.model.dto.CustomerResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class CustomerMapper {

    public CustomerResponse mapToDto(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setBalance(customer.getBalance());
        customerResponse.setCreated(customer.getCreated());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setEnabled(customer.isEnabled());
        customerResponse.setLastname(customer.getLastname());
        customerResponse.setName(customer.getName());
        customerResponse.setBankAccountNumber(customer.getBankAccountNumber());
        customerResponse.setDateOfBirth(customer.getDateOfBirth());

        return customerResponse;
    }

    public Customer map(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setEmail(customerRequest.getEmail());
        customer.setName(customerRequest.getName());
        customer.setLastname(customerRequest.getLastname());
        customer.setPassword(customerRequest.getPassword());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setBankAccountNumber(generateAccountNumber());
        customer.setBalance(BigDecimal.ZERO);
        customer.setEnabled(false);
        customer.setCreated(Instant.now());
        customer.setRole("USER");

        return customer;
    }

    private String generateAccountNumber() {
        String randomNumber = getGeneratedRandomNumber();
        return "4811110000" + randomNumber;
    }

    private String getGeneratedRandomNumber() {
        long randomNumber = 1000000000000000L + (long) (Math.random() * (9999999999999999L-1000000000000000L));
        return String.valueOf(randomNumber);
    }
}
