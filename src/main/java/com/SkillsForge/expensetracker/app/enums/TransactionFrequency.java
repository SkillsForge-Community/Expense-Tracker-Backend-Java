package com.SkillsForge.expensetracker.app.enums;

import java.time.LocalDate;

public enum TransactionFrequency {
    DAILY {
        @Override
        public LocalDate nextDate(LocalDate date) {
            return date.plusDays(1);
        }
    },
    WEEKLY {
        @Override
        public LocalDate nextDate(LocalDate date) {
            return date.plusWeeks(1);
        }
    },
    MONTHLY {
        @Override
        public LocalDate nextDate(LocalDate date) {
            return date.plusMonths(1);
        }
    },
    YEARLY {
        @Override
        public LocalDate nextDate(LocalDate date) {
            return date.plusYears(1);
        }
    };

    public abstract LocalDate nextDate(LocalDate date);
}