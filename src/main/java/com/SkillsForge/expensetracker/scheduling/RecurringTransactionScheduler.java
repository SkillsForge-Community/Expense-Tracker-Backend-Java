package com.SkillsForge.expensetracker.scheduling;

import com.SkillsForge.expensetracker.service.RecurringTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecurringTransactionScheduler {

  private final RecurringTransactionService recurringTransactionService;

  /**
   * Runs every day at midnight (00:00:00) server time. Evaluates all active recurring transactions
   * and creates new standard transactions if they are due.
   */
  @Scheduled(cron = "0 0 0 * * *")
  public void executeRecurringTransactionsDaily() {
    log.info("Starting daily execution cycle for recurring transactions...");
    recurringTransactionService.runExecutionCycle();
    log.info("Completed daily execution cycle for recurring transactions.");
  }
}
