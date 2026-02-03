package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionUpdateRequest {

  private String description;
  private TransactionCategory category;
  private TransactionType type;
  private Long amount;
  private LocalDate date;
}
