package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionFrequency;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RecurringTransactionDto {
    private Long id;
    private String description;
    private TransactionType type;
    private TransactionCategory category;
    private Long amount;
    private LocalDate nextExecutionDate;
    private TransactionFrequency frequency;
    private boolean isActive;

    // It's helpful to show when the cycle started and when it ends
    private LocalDate startDate;
    private LocalDate endDate;
}

