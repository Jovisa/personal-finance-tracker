package com.tw.personalfinancetracker.controller;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionsController {

    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactionsData() {
        return transactionService.getAllTransactions();
    }

    @PostMapping("transactions/new")
    public void addTransaction(@RequestBody Transaction transaction) {
        transactionService.save(transaction);
    }

    @PutMapping("transactions/{id}")
    public void updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        transactionService.update(transaction);
    }

    @DeleteMapping("transactions/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
