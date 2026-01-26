package com.SkillsForge.expensetracker.persistence.entity;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Accessors(chain = true)
@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@SuperBuilder
@RequiredArgsConstructor
public class Transaction extends BaseEntity {
  @Column(nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionCategory category;

  @Column(nullable = false)
  private LocalDate date; //the date the transaction was made

  @Column(nullable = false)
  private Long amount; //We save amount in kobo value so 1 naira will be saved as 100. 1.50 naira will be saved as 150
}
