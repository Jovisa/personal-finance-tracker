package com.tw.personalfinancetracker.service;

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

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;


    public void save(Transaction transaction) {
        repository.save(transaction);
    }

    public void update(Transaction transaction) {
        repository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        if (!repository.existsById(transactionId)) {
            throw new TransactionNotFoundException("Transaction you were trying to delete doesn't exist");
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

    private boolean isAdmin(List<String> userAuthorities) {
        return userAuthorities
                .stream()
                .anyMatch(ROLE_ADMIN::equals);
    }

//    public TransactionDataResponse getAllTransactions(String userId, String typeFilter) {
//
//        List<Transaction> transactions = getListOfAllTransactions(typeFilter)
//                .stream()
//                .filter(t -> Objects.equals(userId, t.getUserId()))
//                .toList();
//
//        return new TransactionDataResponse(transactions);
//    }

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
