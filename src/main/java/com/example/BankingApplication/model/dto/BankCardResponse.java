package com.example.BankingApplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankCardResponse {
    private String cardNumber;

    private String cvc;

    private String pin;

    private boolean enabled;

    private LocalDate expiryDate;
}
