package com.tw.personalfinancetracker.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonDeserialize(builder = TransactionDataResponse.TransactionDataResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Summary {
    private Double totalIncome;
    private Double totalExpense;
    private Double balance;
}
