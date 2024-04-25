package com.example.BankingApplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionResponse {
    private Long id;

    private String senderName;

    private String senderBankNumber;

    private String title;

    private String recipient;

    private String recipientAccountNumber;

    private BigDecimal amount;

    private Instant date;
}
