package com.example.BankingApplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_card")
public class BankCard extends AbstractModel {

    @Size(min = 16, max = 16)
    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Size(min = 3, max = 3)
    @Column(name = "cvc")
    private String cvc;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "enabled")
    private boolean enabled;

    @Pattern(regexp = "^[0-9]{4}$", message = "must be 4 numbers")
    @Column(name = "pin")
    private String pin;

    @OneToOne(mappedBy = "bankCard")
    private Customer customer;
}
