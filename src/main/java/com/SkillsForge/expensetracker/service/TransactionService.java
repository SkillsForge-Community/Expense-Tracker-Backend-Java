package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.app.dto.TransactionUpdateRequest;
import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Transaction updateTransaction(Long id, TransactionUpdateRequest request);

    Page<Transaction> getAllTransactions(
            TransactionFilter filter,
            Pageable pageable
    );
}
