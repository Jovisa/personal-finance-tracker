package com.tw.personalfinancetracker.model.dto;

import com.tw.personalfinancetracker.model.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class TransactionDataResponse {
    private Summary summary;
    private List<Transaction> transactions;

    public TransactionDataResponse(List<Transaction> transactions) {
        this.summary = new Summary(transactions);
        this.transactions = transactions;
    }
}
