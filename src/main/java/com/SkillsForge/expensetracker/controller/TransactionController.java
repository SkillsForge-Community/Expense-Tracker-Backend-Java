package com.SkillsForge.expensetracker.controller;

import com.SkillsForge.expensetracker.app.dto.TransactionDto;
import com.SkillsForge.expensetracker.app.dto.TransactionUpdateRequest;
import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

  private final TransactionService transactionService;

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
