package com.SkillsForge.expensetracker.controller;

import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;
import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.dto.TransactionUpdateRequest;
import com.SkillsForge.expensetracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping
  public ResponseEntity<TransactionDto> createTransaction(
      @RequestBody @Validated CreateTransactionRequest request) {
    TransactionDto response = transactionService.createTransaction(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
    TransactionDto response = transactionService.getTransactionById(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public TransactionDto updateTransaction(
          @PathVariable Long id,
          @RequestBody TransactionUpdateRequest request
  ) {
    return transactionService.updateTransaction(id, request);
  }

  @GetMapping
  public Page<TransactionDto> getAllTransactions(
          @ModelAttribute TransactionFilter filter,
          Pageable pageable
  ) {
    return transactionService.getAllTransactions(filter, pageable);
  }
}
