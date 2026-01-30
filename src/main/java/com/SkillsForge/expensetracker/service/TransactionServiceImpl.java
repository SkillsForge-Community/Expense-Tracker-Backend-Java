package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;
import com.SkillsForge.expensetracker.exception.ResourceNotFoundException;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.persistence.repository.TransactionRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  @Override
  @Transactional
  public TransactionDto createTransaction(CreateTransactionRequest request) {
    LocalDateTime now = LocalDateTime.now();

    Transaction transaction = new Transaction(request);
    transaction.setCreatedAt(now);
    transaction.setUpdatedAt(now);

    Transaction savedTransaction = transactionRepository.save(transaction);
    log.info("Transaction created successfully with ID: {}", savedTransaction.getId());
    return TransactionDto.fromEntity(savedTransaction);
  }

  @Override
  @Transactional(readOnly = true)
  public TransactionDto getTransactionById(Long id) {
    log.info("Fetching transaction with ID: {}", id);

    return TransactionDto.fromEntity(
        transactionRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error("Transaction not found with ID: {}", id);
                  return new ResourceNotFoundException("Transaction not found with ID: " + id);
                }));
  }
}
