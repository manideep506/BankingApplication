package com.example.BankingApplication.controller;

import com.example.BankingApplication.model.dto.BankCardRequest;
import com.example.BankingApplication.model.dto.BankCardResponse;
import com.example.BankingApplication.service.BankCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/bank-card")
@AllArgsConstructor
public class BankCardController {

    private final BankCardService bankCardService;

    @GetMapping("/by-current-customer")
    public ResponseEntity<BankCardResponse> findByCurrentCustomer() {
        return status(HttpStatus.OK).body(bankCardService.findByCurrentCustomer());
    }

    @PostMapping("/create-card")
    public ResponseEntity<Void> createBankCard(@RequestBody BankCardRequest bankCardRequest) {
        bankCardService.createBankCard(bankCardRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
