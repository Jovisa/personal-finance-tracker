package com.tw.personalfinancetracker.controller;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.dto.TransactionDataResponse;
import com.tw.personalfinancetracker.service.TransactionService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/transactions")
    public TransactionDataResponse getTransactionsData(
            @Nullable @RequestParam String filterByType,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return transactionService.getAllTransactions(userDetails, filterByType);
    }

    @PostMapping("transactions/new")
    public void addTransaction(
            @Valid @RequestBody Transaction transaction,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        transaction.setUserId(userDetails.getUsername());
        transactionService.save(transaction);
    }

    @PutMapping("transactions/{id}")
    public void updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody Transaction transaction) {
        transaction.setId(id);
        transactionService.update(transaction);
    }

    @DeleteMapping("transactions/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
