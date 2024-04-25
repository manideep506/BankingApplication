package com.example.BankingApplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionRequest {

    private String title;

    private String recipient;

    private String recipientAccountNumber;

    private BigDecimal amount;
}
