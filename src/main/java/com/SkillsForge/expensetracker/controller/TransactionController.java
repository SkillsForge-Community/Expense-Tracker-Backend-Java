package com.SkillsForge.expensetracker.controller;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/transaction")
public class TransactionController {
  private final TransactionService transactionService;

//  update a transaction by id
  @PutMapping("/{id}")
  public Transaction updateTransaction(
          @PathVariable Long id,
          @RequestBody Transaction transaction
  ) {
    return transactionService.updateTransaction(id, transaction);
  }

//  fetch all transactions with pagination and optional filters
  @GetMapping
  public Page<Transaction> getAllTransactions(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(required = false) TransactionCategory category,
          @RequestParam(required = false) TransactionType type
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return transactionService.getAllTransactions(category, type, pageable);
  }

}
