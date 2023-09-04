package com.tw.personalfinancetracker.model.dto;

import com.tw.personalfinancetracker.model.Transaction;

import java.util.List;

import static com.tw.personalfinancetracker.util.Constants.EXPENSE;
import static com.tw.personalfinancetracker.util.Constants.INCOME;

public class SummaryFactory {
    public static Summary buildSummary(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return Summary.builder().build();
        }

        Double totalIncome = sumAllTransactions(transactions, INCOME);
        Double totalExpense = sumAllTransactions(transactions, EXPENSE);

        return Summary.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(totalIncome - totalExpense)
                .build();
    }

    public static Summary buildSummary(List<Transaction> transactions, String filterByType) {
        if (transactions.isEmpty()) {
            return Summary.builder().build();
        }

        return INCOME.equals(filterByType)
                ? Summary.builder()
                .totalIncome(sumAllTransactions(transactions, INCOME))
                .build()
                : Summary.builder()
                .totalExpense(sumAllTransactions(transactions, EXPENSE))
                .build();
    }

    private static Double sumAllTransactions(List<Transaction> transactions, String transactionType) {
        return transactions.stream()
                .filter(t -> transactionType.equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }
}
