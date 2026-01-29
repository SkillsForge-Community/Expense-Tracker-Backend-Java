package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.TransactionDto;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;

public interface TransactionService {
    //Transaction createTransaction(CreateTransactionRequest request);
    TransactionDto createTransaction(TransactionDto transactionDto);
    Transaction getTransactionById(Long id);
}
