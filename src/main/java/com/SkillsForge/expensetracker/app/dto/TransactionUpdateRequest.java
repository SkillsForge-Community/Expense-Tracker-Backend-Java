package com.SkillsForge.expensetracker.app.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionUpdateRequest {

    private String description;
    private TransactionCategory category;
    private TransactionType type;
    private Long amount;
    private LocalDate date;
}
