package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionDto(
        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "Transaction Type is Required")
        TransactionType type,

        @NotBlank(message = "Category is required")
        TransactionCategory category,

        @NotBlank(message = "Transaction date is required")
        LocalDate date,

        @NotBlank(message = "Amount is required")
        @Positive(message = "Amount must be a Positive Integer")
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