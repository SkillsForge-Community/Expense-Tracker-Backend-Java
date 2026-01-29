package com.SkillsForge.expensetracker.service;


import com.SkillsForge.expensetracker.app.dto.TransactionUpdateRequest;
import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.persistence.repository.TransactionRepository;
import com.SkillsForge.expensetracker.persistence.specification.TransactionSpecification;
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
    public Transaction updateTransaction(Long id, TransactionUpdateRequest request) {

        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setDescription(request.getDescription());
        existing.setCategory(request.getCategory());
        existing.setType(request.getType());
        existing.setAmount(request.getAmount());
        existing.setDate(request.getDate());
        existing.setUpdatedAt(LocalDateTime.now());

        return transactionRepository.save(existing);
    }

    @Override
    public Page<Transaction> getAllTransactions(
            TransactionFilter filter,
            Pageable pageable
    ) {
        return transactionRepository.findAll(
                TransactionSpecification.withFilters(filter),
                pageable
        );
    }


}
