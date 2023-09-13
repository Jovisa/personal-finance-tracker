package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.exception.PermissionDeniedException;
import com.tw.personalfinancetracker.exception.TransactionNotFoundException;
import com.tw.personalfinancetracker.exception.WrongFilterException;
import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.TransactionServiceRequest;
import com.tw.personalfinancetracker.model.dto.TransactionDataResponse;
import com.tw.personalfinancetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.tw.personalfinancetracker.util.Constants.ROLE_ADMIN;
import static com.tw.personalfinancetracker.util.Constants.ROLE_USER;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;


    public void save(Transaction transaction) {
        repository.save(transaction);
    }

    public void update(Transaction transaction, TransactionServiceRequest request) {

        if (!repository.existsById(transaction.getId())) {
            throw new TransactionNotFoundException("Transaction you're trying to update doesn't exist");
        }

        if (isAdmin(request.getUserAuthorities())) {
            repository.save(transaction);
        } else {
            handleUserUpdate(transaction, request.getUserId());
        }
    }

    private void handleUserUpdate(Transaction transaction, String userId) {
        if (!isOwner(transaction.getId(), userId)) {
            throw new PermissionDeniedException("Permission Denied, you can only update your Transactions");
        }

        transaction.setUserId(userId);
        repository.save(transaction);
    }

    private boolean isOwner(Long transactionId, String userId) {
        var transaction = repository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction doesn't exist"));
        return userId.equals(transaction.getUserId());
    }


    public void deleteTransaction(Long transactionId, TransactionServiceRequest request) {

        if (!repository.existsById(transactionId)) {
            throw new TransactionNotFoundException("Transaction you were trying to delete doesn't exist");
        }

        if (isAdmin(request.getUserAuthorities())) {
            repository.deleteById(transactionId);
        } else {
            handleUserDelete(transactionId, request.getUserId());
        }

    }

    private void handleUserDelete(Long transactionId, String userId) {
        if (!isOwner(transactionId, userId)) {
            throw new PermissionDeniedException("Permission Denied, you can only delete your own Transactions");
        }

        repository.deleteById(transactionId);
    }


    public TransactionDataResponse getAllTransactions(TransactionServiceRequest request) {
        List<Transaction> transactions = getListOfAllTransactions(request.getTypeFilter());

        if (isAdmin(request.getUserAuthorities())) {
            return new TransactionDataResponse(transactions);
        }

        var userTransactions = transactions.stream()
                .filter(t -> Objects.equals(request.getUserId(), t.getUserId()))
                .toList();

        return new TransactionDataResponse(userTransactions);
    }

    private boolean hasAuthority(List<String> userAuthorities, String authority) {
        return userAuthorities
                .stream()
                .anyMatch(authority::equals);
    }

    private boolean isAdmin(List<String> userAuthorities) {
        return hasAuthority(userAuthorities, ROLE_ADMIN);
    }

    private boolean isUser(List<String> userAuthorities) {
        return hasAuthority(userAuthorities, ROLE_USER);
    }

    public List<Transaction> getListOfAllTransactions(String typeFilter) {
        if (typeFilter == null) {
            return repository.findAll();
        }
        if (!typeFilter.matches("^(income|expense)$")) {
            throw new WrongFilterException("type must be 'income' or 'expense'");
        }
        return repository.findAll()
                .stream()
                .filter(t -> t.getType().equals(typeFilter))
                .toList();
    }
}
