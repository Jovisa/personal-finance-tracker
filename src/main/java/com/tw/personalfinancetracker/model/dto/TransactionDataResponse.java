package com.tw.personalfinancetracker.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tw.personalfinancetracker.model.Transaction;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@JsonDeserialize(builder = TransactionDataResponse.TransactionDataResponseBuilder.class)
public class TransactionDataResponse {
    private Summary summary;
    private List<Transaction> transactions;
}
