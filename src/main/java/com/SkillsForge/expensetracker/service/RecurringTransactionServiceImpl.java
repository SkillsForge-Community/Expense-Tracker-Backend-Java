package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.CreateRecurringTransactionRequest;
import com.SkillsForge.expensetracker.dto.RecurringTransactionDto;
import com.SkillsForge.expensetracker.dto.RecurringUpdateBatchRequest;
import com.SkillsForge.expensetracker.exception.ResourceNotFoundException;
import com.SkillsForge.expensetracker.persistence.entity.RecurringTransaction;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.persistence.entity.User;
import com.SkillsForge.expensetracker.persistence.repository.RecurringTransactionRepository;
import com.SkillsForge.expensetracker.persistence.repository.TransactionRepository;
import com.SkillsForge.expensetracker.persistence.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecurringTransactionServiceImpl implements RecurringTransactionService {

    private final RecurringTransactionRepository recurringTransactionRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RecurringTransactionDto createRecurringTransaction(
            CreateRecurringTransactionRequest request) {
        User user = getCurrentUser();

        RecurringTransaction recurringTransaction =
                RecurringTransaction.builder()
                        .description(request.getDescription())
                        .type(request.getType())
                        .category(request.getCategory())
                        .amount(request.getAmount())
                        .frequency(request.getFrequency())
                        .nextExecutionDate(request.getStartDate())
                        .isActive(true)
                        .user(user)
                        .build();
        recurringTransaction.setCreatedAt(LocalDateTime.now());
        recurringTransaction.setUpdatedAt(LocalDateTime.now());

        RecurringTransaction saved = recurringTransactionRepository.save(recurringTransaction);
        log.info("Recurring Transaction template created with ID: {}", saved.getId());

        return mapToDto(saved);
    }

    @Override
    @Transactional
    public RecurringTransactionDto update(Long id, RecurringUpdateBatchRequest request) {
        User user = getCurrentUser();

        RecurringTransaction existing =
                recurringTransactionRepository
                        .findById(id)
                        .filter(rt -> rt.getUser().getId().equals(user.getId()))
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Recurring Transaction not found with ID: " + id));

        if (Objects.nonNull(request.getCategory())) existing.setCategory(request.getCategory());
        if (Objects.nonNull(request.getAmount())) existing.setAmount(request.getAmount());
        if (Objects.nonNull(request.getCategory())) existing.setCategory(request.getCategory());
        if (Objects.nonNull(request.getFrequency())) existing.setFrequency(request.getFrequency());
        existing.setUpdatedAt(LocalDateTime.now());

        RecurringTransaction saved = recurringTransactionRepository.save(existing);
        log.info("Recurring Transaction template updated with ID: {}", saved.getId());

        return mapToDto(saved);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        User user = getCurrentUser();

        RecurringTransaction existing =
                recurringTransactionRepository
                        .findById(id)
                        .filter(rt -> rt.getUser().getId().equals(user.getId()))
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Recurring Transaction not found with ID: " + id));

        existing.setActive(false);
        existing.setUpdatedAt(LocalDateTime.now());
        recurringTransactionRepository.save(existing);
        log.info("Recurring Transaction template deactivated with ID: {}", id);
    }

    @Override
    @Transactional
    public void runExecutionCycle() {
        LocalDate today = LocalDate.now();
        List<RecurringTransaction> dueTransactions =
                recurringTransactionRepository.findByIsActiveTrueAndNextExecutionDateLessThanEqual(today);

        log.info(
                "Found {} active recurring transactions due for execution today.", dueTransactions.size());

        for (RecurringTransaction rt : dueTransactions) {
            try {
                Transaction transaction =
                        Transaction.builder()
                                .description(rt.getDescription())
                                .type(rt.getType())
                                .category(rt.getCategory())
                                .amount(rt.getAmount())
                                .date(rt.getNextExecutionDate())
                                .user(rt.getUser())
                                .build();

                transaction.setCreatedAt(LocalDateTime.now());
                transaction.setUpdatedAt(LocalDateTime.now());

                transactionRepository.save(transaction);

                rt.setNextExecutionDate(rt.getFrequency().nextDate(rt.getNextExecutionDate()));
                rt.setUpdatedAt(LocalDateTime.now());
                recurringTransactionRepository.save(rt);

                log.info(
                        "Executed recurring transaction ID: {}. Next execution date set to: {}",
                        rt.getId(),
                        rt.getNextExecutionDate());
            } catch (Exception e) {
                log.error("Failed to execute recurring transaction ID: {}", rt.getId(), e);
            }
        }
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private RecurringTransactionDto mapToDto(RecurringTransaction entity) {
        return RecurringTransactionDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .type(entity.getType())
                .category(entity.getCategory())
                .amount(entity.getAmount())
                .nextExecutionDate(entity.getNextExecutionDate())
                .frequency(entity.getFrequency())
                .isActive(entity.isActive())
                .build();
    }
}
