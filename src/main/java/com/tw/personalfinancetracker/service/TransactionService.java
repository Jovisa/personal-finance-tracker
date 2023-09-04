package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.exception.TransactionNotFoundException;
import com.tw.personalfinancetracker.exception.WrongFilterException;
import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.dto.Summary;
import com.tw.personalfinancetracker.model.dto.SummaryFactory;
import com.tw.personalfinancetracker.model.dto.TransactionDataResponse;
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

    public TransactionDataResponse getAllTransactions() {
        List<Transaction> transactions = repository.findAll();
        Summary summary = SummaryFactory.buildSummary(transactions); // SumaryFactory
        return TransactionDataResponse.builder()
                .summary(summary)
                .transactions(transactions)
                .build();
    }


    public TransactionDataResponse getAllTransactions(String filterByType) {
        validateFilter(filterByType);

        List<Transaction> transactions = repository.findAll()
                .stream()
                .filter(t -> t.getType().equals(filterByType))
                .toList();

        Summary summary = SummaryFactory.buildSummary(transactions, filterByType);

        return TransactionDataResponse.builder()
                .summary(summary)
                .transactions(transactions)
                .build();
    }

    private void validateFilter(String typeToFilter) {
        if (!typeToFilter.matches("^(income|expense)$")) {
            throw new WrongFilterException("type must be 'income' or 'expense'");
        }
    }
}
