package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.TransactionCategory;
import com.SkillsForge.expensetracker.app.enums.TransactionType;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String description;
    private TransactionType type;
    private TransactionCategory category;
    private LocalDate date;
    private Long amount; // Amount in kobo
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // method to convert Transaction entity to TransactionResponse DTO

    public static TransactionResponse fromEntity(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .type(transaction.getType())
                .category(transaction.getCategory())
                .date(transaction.getDate())
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
