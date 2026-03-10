package com.SkillsForge.expensetracker.service;


import com.SkillsForge.expensetracker.dto.CreateTransactionRequest;
import com.SkillsForge.expensetracker.dto.TransactionDto;
import org.springframework.data.domain.Page;


import java.awt.print.Pageable;

public interface RecurringExpense {
    TransactionDto createRecurringTransaction(CreateTransactionRequest request);
    TransactionDto getRecurringTransactionById(Long id);
    TransactionDto updateRecurringTransaction(Long id, CreateTransactionRequest request);
    TransactionDto deleteRecurringTransaction(Long id);
    Page<TransactionDto> getRecurringTransactions(Pageable pageable);
}
