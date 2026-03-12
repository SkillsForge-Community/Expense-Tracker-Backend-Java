package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionFrequency;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecurringUpdateBatchRequest {
  private String description;
  private Long amount; // New future price
  private TransactionCategory category;
  private TransactionFrequency frequency; // e.g., changing from Weekly to Monthly
  private LocalDate endDate; // To set a limit on an existing subscription
}
