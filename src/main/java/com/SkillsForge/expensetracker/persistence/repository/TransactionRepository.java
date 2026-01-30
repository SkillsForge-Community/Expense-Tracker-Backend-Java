package com.SkillsForge.expensetracker.persistence.repository;

import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
