package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;

public interface TransactionService {
  TransactionDto createTransaction(CreateTransactionRequest request);

  TransactionDto getTransactionById(Long id);
}
