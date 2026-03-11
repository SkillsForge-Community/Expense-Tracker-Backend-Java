package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionFrequency;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateRecurringTransactionRequest {

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Transaction type is required")
    private TransactionType type; // INCOME or EXPENSE

    @NotNull(message = "Category is required")
    private TransactionCategory category;

    @Positive(message = "Amount must be greater than zero")
    private Long amount; // Still in Kobo

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "Frequency is required")
    private TransactionFrequency frequency; // DAILY, WEEKLY, etc.

    // Optional: If null, it runs forever.
    // If set, the scheduler stops after this date.
    private LocalDate endDate;
}