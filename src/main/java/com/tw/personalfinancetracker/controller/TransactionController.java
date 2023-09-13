package com.tw.personalfinancetracker.controller;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.TransactionServiceRequest;
import com.tw.personalfinancetracker.model.dto.TransactionDataResponse;
import com.tw.personalfinancetracker.service.TransactionService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("")
    public TransactionDataResponse getTransactionsData(
            @Nullable @RequestParam String typeFilter,
            @AuthenticationPrincipal UserDetails user
    ) {
        var serviceRequest = new TransactionServiceRequest(user, typeFilter);
        return transactionService.getAllTransactions(serviceRequest);
    }

    @PostMapping("")
    public void addTransaction(
            @Valid @RequestBody Transaction transaction,
            @AuthenticationPrincipal UserDetails user
    ) {
        transaction.setUserId(user.getUsername());
        transactionService.save(transaction);
    }

    @PutMapping("/{id}")
    public void updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody Transaction transaction,
            @AuthenticationPrincipal UserDetails user
    ) {
        transaction.setId(id);
        var serviceRequest = new TransactionServiceRequest(user);
        transactionService.update(transaction, serviceRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user
    ) {
        transactionService.deleteTransaction(id, new TransactionServiceRequest(user));
    }

}
