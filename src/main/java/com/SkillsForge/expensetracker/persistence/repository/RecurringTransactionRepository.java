package com.SkillsForge.expensetracker.persistence.repository;

import com.SkillsForge.expensetracker.persistence.entity.RecurringTransaction;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {

  // Find all active recurring transactions that are due for execution
  // (nextExecutionDate <= today)
  List<RecurringTransaction> findByIsActiveTrueAndNextExecutionDateLessThanEqual(LocalDate date);

  // Find all recurring transactions for a specific user
  List<RecurringTransaction> findByUserId(Long userId);
}
