package com.SkillsForge.expensetracker.app.filter;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionFilter {
    private TransactionCategory category;
    private TransactionType type;
}
