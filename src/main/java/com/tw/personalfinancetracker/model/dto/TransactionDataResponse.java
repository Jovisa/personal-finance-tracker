package com.tw.personalfinancetracker.model.dto;

import com.tw.personalfinancetracker.model.Transaction;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class TransactionDataResponse {
    private Summary summary;
    private List<Transaction> transactions;
}
