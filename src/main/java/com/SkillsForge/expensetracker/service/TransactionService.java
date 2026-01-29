package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.TransactionDto;

public interface TransactionService {
    //Transaction createTransaction(CreateTransactionRequest request);
    TransactionDto createTransaction(TransactionDto transactionDto);
    TransactionDto getTransactionById(Long id);
}
