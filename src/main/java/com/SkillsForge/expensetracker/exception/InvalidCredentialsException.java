package com.SkillsForge.expensetracker.exception;

/** Exception thrown when authentication fails due to invalid credentials */
public class InvalidCredentialsException extends RuntimeException {
  public InvalidCredentialsException(String message) {
    super(message);
  }
}
