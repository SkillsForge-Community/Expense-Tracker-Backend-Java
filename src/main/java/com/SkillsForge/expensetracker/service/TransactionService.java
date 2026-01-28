package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    // update an existing transaction
    Transaction updateTransaction(Long id, Transaction transaction);

    // fetch all transactions with pagination
    Page<Transaction> getAllTransactions(
            TransactionCategory category,
            TransactionType type,
            Pageable pageable
    );

}
