package com.SkillsForge.expensetracker.app.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {

    private Long id;
    private String description;
    private TransactionCategory category;
    private TransactionType type;
    private Long amount;
    private LocalDate date;
    private LocalDateTime createdAt;
}
