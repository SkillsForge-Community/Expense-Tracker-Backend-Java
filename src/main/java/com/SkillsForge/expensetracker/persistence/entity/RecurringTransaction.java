package com.SkillsForge.expensetracker.persistence.entity;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import jakarta.persistence.*;
import jdk.jfr.Frequency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "recurring_transactions")
@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class RecurringTransaction extends BaseEntity {

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategory category;

    @Column(nullable = false)
    private Long amount; // Still in Kobo

    @Column(nullable = false)
    private LocalDate nextExecutionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency; // DAILY, WEEKLY, MONTHLY, etc.

    @Column(nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}