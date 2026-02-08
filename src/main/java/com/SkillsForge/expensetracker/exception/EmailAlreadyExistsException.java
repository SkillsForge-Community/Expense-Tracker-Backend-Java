package com.SkillsForge.expensetracker.exception;

/** Exception thrown when a user attempts to register with an email that already exists */
public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String message) {
    super(message);
  }
}
