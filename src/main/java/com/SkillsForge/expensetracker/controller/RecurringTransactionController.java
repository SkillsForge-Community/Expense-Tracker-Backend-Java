package com.SkillsForge.expensetracker.controller;

import com.SkillsForge.expensetracker.dto.CreateRecurringTransactionRequest;
import com.SkillsForge.expensetracker.dto.RecurringTransactionDto;
import com.SkillsForge.expensetracker.dto.RecurringUpdateBatchRequest;
import com.SkillsForge.expensetracker.service.RecurringTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recurring-transactions")
public class RecurringTransactionController {

  private final RecurringTransactionService recurringTransactionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('TRANSACTION_WRITE')")
  public RecurringTransactionDto createRecurringTransaction(
      @RequestBody @Validated CreateRecurringTransactionRequest request) {
    return recurringTransactionService.createRecurringTransaction(request);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('TRANSACTION_WRITE')")
  public RecurringTransactionDto updateRecurringTransaction(
      @PathVariable Long id, @RequestBody @Validated RecurringUpdateBatchRequest request) {
    return recurringTransactionService.update(id, request);
  }

  @PatchMapping("/{id}/deactivate")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('TRANSACTION_WRITE')")
  public void deactivateRecurringTransaction(@PathVariable Long id) {
    recurringTransactionService.deactivate(id);
  }
}
