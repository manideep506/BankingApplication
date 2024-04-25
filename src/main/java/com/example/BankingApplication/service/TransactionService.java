package com.example.BankingApplication.service;

import com.example.BankingApplication.exceptions.TransactionException;
import com.example.BankingApplication.mapper.TransactionMapper;
import com.example.BankingApplication.model.Customer;
import com.example.BankingApplication.model.Transaction;
import com.example.BankingApplication.model.dto.TransactionRequest;
import com.example.BankingApplication.model.dto.TransactionResponse;
import com.example.BankingApplication.repo.TransactionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final TransactionMapper transactionMapper;
    private final CustomerService customerService;

//    finds transactions by current customer
    public List<TransactionResponse> findByCurrentCustomer() {
        Customer customer = customerService.getCurrentCustomer();
        List<Transaction> transactionsAsSender = transactionRepo.findByCustomerId(customer.getId());
        List<Transaction> transactionsAsRecipient = transactionRepo.findByRecipientAccountNumber(customer.getBankAccountNumber());
        Set<Long> customerIds = transactionsAsRecipient.stream().map(Transaction::getCustomerId).collect(Collectors.toSet());
        List<Customer> senders = customerService.findAllById(customerIds);
        List<TransactionResponse> transactionAsSenderResponse = transactionsAsSender.stream().map(transaction -> transactionMapper.mapToDto(transaction, customer)).collect(Collectors.toList());
        List<TransactionResponse> transactionAsRecipientResponse = transactionsAsRecipient.stream().map(transaction -> transactionMapper.mapToDto(transaction, senders)).collect(Collectors.toList());

        return Stream.concat(transactionAsRecipientResponse.stream(), transactionAsSenderResponse.stream()).collect(Collectors.toList());
    }

//    creates transaction
    public void createTransaction(TransactionRequest transactionRequest) throws TransactionException {
        Customer customer = customerService.getCurrentCustomer();
        Customer recipient = customerService.findByAccountNumber(transactionRequest.getRecipientAccountNumber());
        transferFunds(customer, recipient, transactionRequest.getAmount());
        Transaction transaction = transactionMapper.map(transactionRequest, customer.getId());
        transactionRepo.save(transaction);
    }

//    transfers money from sender to recipient
    private void transferFunds(Customer customer, Customer recipient, BigDecimal amount) throws TransactionException {
        if (!isAmountGreaterThanZero(amount)) {
            throw new TransactionException("Amount must be greater than 0!");
        }
        if (isAmountGreaterThanBalance(customer.getBalance(), amount)) {
            throw new TransactionException("Amount must be lower than balance");
        }
        customer.setBalance(customer.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));
        customerService.editCustomerBalance(customer);
        customerService.editCustomerBalance(recipient);

    }

    private boolean isAmountGreaterThanBalance(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) < 0;
    }

    private boolean isAmountGreaterThanZero(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public TransactionResponse findById(Long id) throws TransactionException {
        Customer customer = customerService.getCurrentCustomer();
        return transactionMapper.mapToDto(transactionRepo.findById(id).orElseThrow(() -> new TransactionException("Transaction not found")), customer);
    }
}
