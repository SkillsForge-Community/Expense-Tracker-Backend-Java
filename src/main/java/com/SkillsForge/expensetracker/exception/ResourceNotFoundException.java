package com.SkillsForge.expensetracker.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Transaction not found with id: " + id);
    }
}
