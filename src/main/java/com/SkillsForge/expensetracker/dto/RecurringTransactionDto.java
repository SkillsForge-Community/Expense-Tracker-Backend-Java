package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionFrequency;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
