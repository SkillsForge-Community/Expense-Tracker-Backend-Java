package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.CreateRecurringTransactionRequest;
import com.SkillsForge.expensetracker.dto.RecurringTransactionDto;
import com.SkillsForge.expensetracker.dto.RecurringUpdateBatchRequest;

public interface RecurringTransactionService {
  RecurringTransactionDto createRecurringTransaction(CreateRecurringTransactionRequest request);

  // only updates the templates for all future transactions
  RecurringTransactionDto update(Long id, RecurringUpdateBatchRequest request);

  void deactivate(Long id);

  void runExecutionCycle();
}
