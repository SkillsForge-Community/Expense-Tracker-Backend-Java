package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.app.dto.TransactionDto;
import com.SkillsForge.expensetracker.app.dto.TransactionUpdateRequest;
import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.persistence.repository.TransactionRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDto updateTransaction(Long id, TransactionUpdateRequest request) {

        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setDescription(request.getDescription());
        existing.setCategory(request.getCategory());
        existing.setType(request.getType());
        existing.setAmount(request.getAmount());
        existing.setDate(request.getDate());
        existing.setUpdatedAt(LocalDateTime.now());

        Transaction saved = transactionRepository.save(existing);

        return mapToDto(saved);
    }

    @Override
    public Page<TransactionDto> getAllTransactions(
            TransactionFilter filter,
            Pageable pageable
    ) {

        Specification<Transaction> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), filter.getCategory()));
            }

            if (filter.getType() != null) {
                predicates.add(cb.equal(root.get("type"), filter.getType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return transactionRepository.findAll(spec, pageable)
                .map(this::mapToDto);
    }

    private TransactionDto mapToDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .category(transaction.getCategory())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
