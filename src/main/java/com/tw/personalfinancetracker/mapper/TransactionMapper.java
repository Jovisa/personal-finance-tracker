package com.tw.personalfinancetracker.mapper;


import com.tw.personalfinancetracker.model.entity.Transaction;
import com.tw.personalfinancetracker.model.dto.request.TransactionRequest;
import org.springframework.stereotype.Component;


@Component
public class TransactionMapper {


    public Transaction toTransaction(
            Transaction transaction,
            TransactionRequest transactionRequest
    ) {
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        return transaction;
    }


    public Transaction toTransaction(
            TransactionRequest transactionRequest,
            String userId
    ) {
        return new Transaction(
                userId,
                transactionRequest.getType(),
                transactionRequest.getAmount(),
                transactionRequest.getDescription());
    }

}
