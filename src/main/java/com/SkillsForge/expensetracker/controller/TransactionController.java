package com.SkillsForge.expensetracker.controller;

import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionResponse;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/transaction")
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping
  public ResponseEntity<TransactionResponse> createTransaction(
      @Valid @RequestBody CreateTransactionRequest request) {

    log.info("Received request to create transaction: {}", request.getDescription());

    Transaction transaction = transactionService.createTransaction(request);
    TransactionResponse response = TransactionResponse.fromEntity(transaction);

    log.info("Transaction created successfully with ID: {}", response.getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {

    log.info("Received request to fetch transaction with ID: {}", id);

    Transaction transaction = transactionService.getTransactionById(id);
    TransactionResponse response = TransactionResponse.fromEntity(transaction);

    return ResponseEntity.ok(response);
  }
}
