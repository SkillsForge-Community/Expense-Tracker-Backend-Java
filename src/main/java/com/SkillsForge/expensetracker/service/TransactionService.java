package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;

public interface TransactionService {
    //Transaction createTransaction(CreateTransactionRequest request);
    TransactionDto createTransaction(CreateTransactionRequest request);
    TransactionDto getTransactionById(Long id);
}
