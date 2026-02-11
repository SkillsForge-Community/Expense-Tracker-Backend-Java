package com.SkillsForge.expensetracker.controller;

import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;
import com.SkillsForge.expensetracker.dto.TransactionUpdateRequest;
import com.SkillsForge.expensetracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('TRANSACTION_WRITE')")
  public TransactionDto createTransaction(
      @RequestBody @Validated CreateTransactionRequest request) {
    return transactionService.createTransaction(request);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('TRANSACTION_READ')")
  public TransactionDto getTransactionById(@PathVariable Long id) {
    return transactionService.getTransactionById(id);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('TRANSACTION_WRITE')")
  public TransactionDto updateTransaction(
      @PathVariable Long id, @RequestBody TransactionUpdateRequest request) {
    return transactionService.updateTransaction(id, request);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('TRANSACTION_READ')")
  public Page<TransactionDto> getAllTransactions(
      @ModelAttribute TransactionFilter filter, Pageable pageable) {
    return transactionService.getAllTransactions(filter, pageable);
  }
}
