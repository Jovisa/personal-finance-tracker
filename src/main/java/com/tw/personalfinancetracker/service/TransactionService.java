package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.exception.TransactionNotFoundException;
import com.tw.personalfinancetracker.exception.WrongFilterException;
import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }


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

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public List<Transaction> getAllTransactions(String filterByType) {
        validateFilter(filterByType);
        return repository
                .findAll()
                .stream()
                .filter(t -> t.getType().equals(filterByType))
                .toList();
    }

    private void validateFilter(String typeToFilter) {
        if (!typeToFilter.matches("^(income|expense)$")) {
            throw new WrongFilterException("type must be 'income' or 'expense'");
        }
    }
}
