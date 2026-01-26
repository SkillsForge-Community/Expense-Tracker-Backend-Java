package com.SkillsForge.expensetracker.controller;

import com.SkillsForge.expensetracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/transaction")
public class TransactionController {
  private final TransactionService transactionService;
}
