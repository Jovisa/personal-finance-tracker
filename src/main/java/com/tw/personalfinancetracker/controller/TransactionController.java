package com.tw.personalfinancetracker.controller;

import com.tw.personalfinancetracker.model.TransactionServiceRequest;
import com.tw.personalfinancetracker.model.dto.request.TransactionRequest;
import com.tw.personalfinancetracker.model.dto.response.TransactionDataResponse;
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
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        var serviceRequest = TransactionServiceRequest
                .builder()
                .request(request)
                .userId(user.getUsername())
                .build();

        transactionService.save(serviceRequest);
    }

    @PutMapping("/{id}")
    public void updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        var serviceRequest = new TransactionServiceRequest(request, id, user);
        transactionService.update(serviceRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user
    ) {
        transactionService.deleteTransaction(new TransactionServiceRequest(user, id));
    }

}
