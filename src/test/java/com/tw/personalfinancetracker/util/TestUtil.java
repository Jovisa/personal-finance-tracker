package com.tw.personalfinancetracker.util;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.dto.SummaryFactory;
import com.tw.personalfinancetracker.model.dto.TransactionDataResponse;

import java.util.List;

public class TestUtil {
    public static TransactionDataResponse generateResponse() {
        final List<Transaction> transactions = List.of(
                new Transaction( 1L, "income", 1000.0, ""),
                new Transaction( 2L, "income", 3400.0, ""),
                new Transaction( 3L, "income", 500.00, ""),
                new Transaction(4L, "expense", 257.0, ""),
                new Transaction(5L, "expense", 2457.0, ""),
                new Transaction(6L, "expense", 21.0, "")
        );

        return TransactionDataResponse.builder()
                .summary(SummaryFactory.buildSummary(transactions))
                .transactions(transactions)
                .build();
    }
}
