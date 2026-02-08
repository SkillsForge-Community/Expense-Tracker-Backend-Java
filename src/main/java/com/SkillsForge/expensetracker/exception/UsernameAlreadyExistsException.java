package com.SkillsForge.expensetracker.exception;

/** Exception thrown when a user attempts to register with a username that already exists */
public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String message) {
    super(message);
  }
}
