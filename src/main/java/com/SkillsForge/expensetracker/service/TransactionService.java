package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;
import com.SkillsForge.expensetracker.dto.TransactionUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
  TransactionDto createTransaction(CreateTransactionRequest request);

  TransactionDto getTransactionById(Long id);

  TransactionDto updateTransaction(Long id, TransactionUpdateRequest request);

  Page<TransactionDto> getAllTransactions(TransactionFilter filter, Pageable pageable);
}
