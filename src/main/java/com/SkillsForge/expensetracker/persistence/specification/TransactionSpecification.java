package com.SkillsForge.expensetracker.persistence.specification;

import com.SkillsForge.expensetracker.app.filter.TransactionFilter;
import com.SkillsForge.expensetracker.persistence.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {

    public static Specification<Transaction> withFilters(TransactionFilter filter) {

        return (root, query, cb) -> {

            var predicate = cb.conjunction();

            if (filter.getCategory() != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("category"), filter.getCategory())
                );
            }

            if (filter.getType() != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("type"), filter.getType())
                );
            }
            return predicate;
        };

    }
}

