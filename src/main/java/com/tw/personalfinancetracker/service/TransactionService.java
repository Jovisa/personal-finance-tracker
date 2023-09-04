package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.repository.TransactionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionsRepository repository;

    public TransactionService(TransactionsRepository repository) {
        this.repository = repository;
    }


    public void save(Transaction transaction) {
        repository.save(transaction);
    }

    public void update(Transaction transaction) {
        repository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        repository.deleteById(transactionId);
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }
}
