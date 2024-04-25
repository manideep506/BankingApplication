package com.example.BankingApplication.service;

import com.example.BankingApplication.exceptions.BankCustomerException;
import com.example.BankingApplication.mapper.BankCardMapper;
import com.example.BankingApplication.model.BankCard;
import com.example.BankingApplication.model.Customer;
import com.example.BankingApplication.model.dto.BankCardRequest;
import com.example.BankingApplication.model.dto.BankCardResponse;
import com.example.BankingApplication.repo.BankCardRepo;
import com.example.BankingApplication.repo.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class BankCardService {
    private final BankCardRepo bankCardRepo;
    private final BankCardMapper bankCardMapper;
    private final CustomerRepo customerRepo;

    public BankCardResponse findByCurrentCustomer() {
        Customer currentCustomer = getCurrentCustomer();
        return bankCardMapper.mapToDto(currentCustomer.getBankCard());
    }

    @Transactional(readOnly = true)
    public Customer getCurrentCustomer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepo.findByEmail(email).orElseThrow(() -> new BankCustomerException("Customer not found"));
    }

    public void createBankCard(BankCardRequest bankCardRequest) {
        Customer currentCustomer = getCurrentCustomer();
        BankCard bankCard = bankCardMapper.map(bankCardRequest, currentCustomer);
        bankCardRepo.save(bankCard);
        setCustomerCard(currentCustomer, bankCard);
    }

    @Transactional
    public void setCustomerCard(Customer currentCustomer, BankCard bankCard) {
        currentCustomer.setBankCard(bankCard);
        customerRepo.save(currentCustomer);
    }
}
