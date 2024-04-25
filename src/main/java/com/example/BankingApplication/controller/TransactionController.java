package com.example.BankingApplication.controller;

import com.example.BankingApplication.exceptions.TransactionException;
import com.example.BankingApplication.model.dto.TransactionRequest;
import com.example.BankingApplication.model.dto.TransactionResponse;
import com.example.BankingApplication.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import pl.pabjan.bankmanagementsystem.exceptions.TransactionException;
//import pl.pabjan.bankmanagementsystem.model.dto.TransactionRequest;
//import pl.pabjan.bankmanagementsystem.model.dto.TransactionResponse;
//import pl.pabjan.bankmanagementsystem.service.TransactionService;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> findByCurrentCustomer() {
        return status(HttpStatus.OK).body(transactionService.findByCurrentCustomer());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findById(@PathVariable Long id) throws TransactionException {
        return status(HttpStatus.OK).body(transactionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest transactionRequest) throws TransactionException {
        transactionService.createTransaction(transactionRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
