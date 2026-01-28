package com.SkillsForge.expensetracker.service;


import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


// Implementation class of transaction service
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {

        // fetch existing transaction by id or throw error
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // update only allowed fields
        existingTransaction
                .setDescription(transaction.getDescription())
                .setAmount(transaction.getAmount())
                .setCategory(transaction.getCategory())
                .setType(transaction.getType())
                .setDate(transaction.getDate());

        // update the edited fields
        existingTransaction.setUpdatedAt(LocalDateTime.now());

        // save back to db
        return transactionRepository.save(existingTransaction);
    }

    @Override
    public Page<Transaction> getAllTransactions(
            TransactionCategory category,
            TransactionType type,
            Pageable pageable
    ) {

        if (category != null && type != null) {
            return transactionRepository.findByCategoryAndType(category, type, pageable);
        }

        return transactionRepository.findAll(pageable);
    }



}
