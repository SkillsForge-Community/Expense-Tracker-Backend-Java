package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;
import com.SkillsForge.expensetracker.dto.TransactionUpdateRequest;
import com.SkillsForge.expensetracker.exception.ResourceNotFoundException;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.persistence.repository.TransactionRepository;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  // ================= CREATE =================
  @Override
  @Transactional
  public TransactionDto createTransaction(CreateTransactionRequest request) {
    LocalDateTime now = LocalDateTime.now();

    Transaction transaction = new Transaction(request);
    transaction.setCreatedAt(now);
    transaction.setUpdatedAt(now);

    Transaction saved = transactionRepository.save(transaction);
    log.info("Transaction created successfully with ID: {}", saved.getId());

    return TransactionDto.fromEntity(saved);
  }

  // ================= GET BY ID =================
  @Override
  @Transactional(readOnly = true)
  public TransactionDto getTransactionById(Long id) {
    log.info("Fetching transaction with ID: {}", id);

    Transaction transaction =
        transactionRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Transaction not found with ID: " + id));

    return TransactionDto.fromEntity(transaction);
  }

  // ================= UPDATE =================
  @Override
  @Transactional
  public TransactionDto updateTransaction(Long id, TransactionUpdateRequest request) {

    Transaction existing =
        transactionRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Transaction not found with ID: " + id));

    existing.setDescription(request.getDescription());
    existing.setCategory(request.getCategory());
    existing.setType(request.getType());
    existing.setAmount(request.getAmount());
    existing.setDate(request.getDate());
    existing.setUpdatedAt(LocalDateTime.now());

    Transaction saved = transactionRepository.save(existing);
    log.info("Transaction updated successfully with ID: {}", saved.getId());

    return TransactionDto.fromEntity(saved);
  }

  // ================= GET ALL (FILTERED) =================
  @Override
  @Transactional(readOnly = true)
  public Page<TransactionDto> getAllTransactions(TransactionFilter filter, Pageable pageable) {

    Specification<Transaction> spec =
        (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (filter.getCategory() != null) {
            predicates.add(cb.equal(root.get("category"), filter.getCategory()));
          }

          if (filter.getType() != null) {
            predicates.add(cb.equal(root.get("type"), filter.getType()));
          }

          return cb.and(predicates.toArray(new Predicate[0]));
        };

    return transactionRepository.findAll(spec, pageable).map(TransactionDto::fromEntity);
  }
}
