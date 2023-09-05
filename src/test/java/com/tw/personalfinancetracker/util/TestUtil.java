package com.tw.personalfinancetracker.util;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.dto.SummaryFactory;
import com.tw.personalfinancetracker.model.dto.TransactionDataResponse;

import java.util.List;

public class TestUtil {

    public static final List<Transaction> TRANSACTIONS = List.of(
            new Transaction( 1L, "1", "income", 1.0, ""),
            new Transaction(2L, "1", "expense", 2.0, "")
    );

    public static TransactionDataResponse generateResponse() {
        final List<Transaction> transactions = List.of(
                new Transaction( 1L, "1", "income", 1000.0, ""),
                new Transaction( 2L, "1",  "income", 3400.0, ""),
                new Transaction( 3L, "1",  "income", 500.00, ""),
                new Transaction(4L, "1", "expense", 257.0, ""),
                new Transaction(5L, "1", "expense", 2457.0, ""),
                new Transaction(6L, "1", "expense", 21.0, "")
        );

        return TransactionDataResponse.builder()
                .summary(SummaryFactory.buildSummary(transactions))
                .transactions(transactions)
                .build();
    }

    public static TransactionDataResponse generateFilteredResponse() {
        final List<Transaction> transactions = List.of(
                new Transaction( 1L, "1", "income", 1000.0, ""),
                new Transaction( 2L, "1", "income", 3400.0, ""),
                new Transaction( 3L, "1", "income", 500.00, "")
        );

        return TransactionDataResponse.builder()
                .summary(SummaryFactory.buildSummary(transactions))
                .transactions(transactions)
                .build();
    }
}
