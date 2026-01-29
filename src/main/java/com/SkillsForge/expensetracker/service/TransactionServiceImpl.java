package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;
import com.SkillsForge.expensetracker.exception.ResourceNotFoundException;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction createTransaction(TransactionDto dto) {
        LocalDateTime now = LocalDateTime.now();

        Transaction transaction = new Transaction(dto);
        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction created successfully with ID: {}", savedTransaction.getId());
        return savedTransaction;
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long id) {
        log.info("Fetching transaction with ID: {}", id);

        return transactionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transaction not found with ID: {}", id);
                    return new ResourceNotFoundException("Transaction not found with ID: " + id);
                });
    }
}
