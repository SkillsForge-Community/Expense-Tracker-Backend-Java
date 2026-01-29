package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionDto(
        Long id,
        String description,
        TransactionType type,
        TransactionCategory category,
        LocalDate date,
        Long amount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
    public static TransactionDto fromEntity(Transaction transaction){
        return new TransactionDto(
        transaction.getId(),
        transaction.getDescription(),
        transaction.getType(),
        transaction.getCategory(),
        transaction.getDate(),
        transaction.getAmount(),
        transaction.getCreatedAt(),
        transaction.getUpdatedAt()
    );
    }
}