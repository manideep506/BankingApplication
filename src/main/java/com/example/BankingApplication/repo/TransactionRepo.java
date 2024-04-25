package com.example.BankingApplication.repo;

import com.example.BankingApplication.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerId(Long id);

    List<Transaction> findByRecipientAccountNumber(String bankAccountNumber);
}
