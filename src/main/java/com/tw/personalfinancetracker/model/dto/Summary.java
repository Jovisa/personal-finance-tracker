package com.tw.personalfinancetracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summary {
    private Double totalIncome;
    private Double totalExpense;
    private Double balance;
}
