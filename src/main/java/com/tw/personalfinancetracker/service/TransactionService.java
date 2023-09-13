package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.exception.PermissionDeniedException;
import com.tw.personalfinancetracker.exception.TransactionNotFoundException;
import com.tw.personalfinancetracker.exception.WrongFilterException;
import com.tw.personalfinancetracker.mapper.TransactionMapper;
import com.tw.personalfinancetracker.model.entity.Transaction;
import com.tw.personalfinancetracker.model.TransactionServiceRequest;
import com.tw.personalfinancetracker.model.dto.response.TransactionDataResponse;
import com.tw.personalfinancetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.tw.personalfinancetracker.util.Constants.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;


    public void save(TransactionServiceRequest serviceRequest) {
        var transaction = mapper.toTransaction(serviceRequest.getRequest(), serviceRequest.getUserId());
        repository.save(transaction);
    }



    public void deleteTransaction(TransactionServiceRequest serviceRequest) {
        Long transactionId = serviceRequest.getTransactionId();
        String userId = serviceRequest.getUserId();

        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(()
                        -> new TransactionNotFoundException("Transaction you were trying to delete doesn't exist"));

        if (isAdmin(serviceRequest.getUserAuthorities())) {
            repository.deleteById(transactionId);

        } else if (isNotOwner(transaction.getUserId(), userId)) {
            throw new PermissionDeniedException("Permission Denied, you can only delete your own Transactions");

        } else {
            repository.deleteById(transactionId);
        }
    }

    public void update(TransactionServiceRequest serviceRequest) {
        Transaction transaction = repository.findById(serviceRequest.getTransactionId())
                .orElseThrow(()
                        -> new TransactionNotFoundException("Transaction you're trying to update doesn't exist"));

        transaction = mapper.toTransaction(transaction, serviceRequest.getRequest());

        if (isAdmin(serviceRequest.getUserAuthorities())) {
            repository.save(transaction);
        } else {
            handleUserUpdate(transaction, serviceRequest.getUserId());
        }
    }

    private void handleUserUpdate(Transaction transaction, String userId) {
        if (isNotOwner(transaction.getUserId(), userId)) {
            throw new PermissionDeniedException("Permission Denied, you can only update your Transactions");
        }

        repository.save(transaction);
    }

    private boolean isNotOwner(String ownerId, String userId) {
        return !userId.equals(ownerId);
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
