package com.SkillsForge.expensetracker.persistence.repository;

import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import com.SkillsForge.expensetracker.persistence.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository
    extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

  Page<Transaction> findAllByUser(User user, Pageable pageable);

  Page<Transaction> findAll(Specification<Transaction> spec, Pageable pageable);

  Optional<Transaction> findByIdAndUser(Long id, User user);
}
