package com.tw.personalfinancetracker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tw.personalfinancetracker.model.entity.Transaction;
import lombok.*;

import java.util.List;

import static com.tw.personalfinancetracker.util.Constants.EXPENSE;
import static com.tw.personalfinancetracker.util.Constants.INCOME;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Summary {
    private Double totalIncome;
    private Double totalExpense;
    private Double balance;

    public Summary(List<Transaction> transactions) {
        if (!transactions.isEmpty()) {
            Double totalIncome = sumAllTransactions(transactions, INCOME);
            Double totalExpense = sumAllTransactions(transactions, EXPENSE);

            this.totalIncome = totalIncome;
            this.totalExpense = totalExpense;
            this.balance = totalIncome - totalExpense;
        }
    }

    private static Double sumAllTransactions(List<Transaction> transactions, String transactionType) {
        return transactions.stream()
                .filter(t -> transactionType.equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }
}
