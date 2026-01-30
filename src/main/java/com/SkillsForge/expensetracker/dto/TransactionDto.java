package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;

import java.time.LocalDate;

public record TransactionDto(
        String description,
        TransactionType type,
        TransactionCategory category,
        LocalDate date,
        Long amount
){
    public static TransactionDto fromEntity(Transaction transaction){
        return new TransactionDto(
        transaction.getDescription(),
        transaction.getType(),
        transaction.getCategory(),
        transaction.getDate(),
        transaction.getAmount()
    );
    }
}