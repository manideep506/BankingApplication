package com.example.BankingApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AbstractModel {

    @Size(min = 1, max = 100)
    @Column(name = "title")
    private String title;

    @Size(min = 2, max = 90)
    @Column(name = "recipient")
    private String recipient;

    @Pattern(regexp = "^[0-9]{26}$", message = "must be 4 numbers")
    @Column(name = "recipient_account_number")
    private String recipientAccountNumber;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date")
    private Instant date;

    @Column(name = "customer_id")
    private Long customerId;
}
