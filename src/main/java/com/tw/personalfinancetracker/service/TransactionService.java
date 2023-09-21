package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.exception.PermissionDeniedException;
import com.tw.personalfinancetracker.exception.TransactionNotFoundException;
import com.tw.personalfinancetracker.exception.WrongFilterException;
import com.tw.personalfinancetracker.mapper.TransactionMapper;
import com.tw.personalfinancetracker.model.TransactionServiceRequest;
import com.tw.personalfinancetracker.model.dto.response.TransactionDataResponse;
import com.tw.personalfinancetracker.model.entity.Transaction;
import com.tw.personalfinancetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.tw.personalfinancetracker.util.UserUtil.isAdmin;
import static com.tw.personalfinancetracker.util.UserUtil.isNotOwner;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;


    public void save(TransactionServiceRequest serviceRequest) {
        var transaction = mapper.toTransaction(
                serviceRequest.getRequest(),
                serviceRequest.getUserId()
        );
        repository.save(transaction);
    }

    public void update(TransactionServiceRequest serviceRequest) {
        executeOperation(serviceRequest, CrudRepository::save);
    }

    public void deleteTransaction(TransactionServiceRequest serviceRequest) {
        executeOperation(serviceRequest, CrudRepository::delete);
    }

    public void executeOperation(
            TransactionServiceRequest serviceRequest,
            Command command
    ) {
        Transaction transaction = repository.findById(serviceRequest.getTransactionId())
                .orElseThrow(()
                        -> new TransactionNotFoundException("Transaction doesn't exist"));

        transaction = mapper.toTransaction(transaction, serviceRequest.getRequest());

        if (isAdmin(serviceRequest.getUserAuthorities())) {
            command.execute(repository, transaction);

        } else if (isNotOwner(transaction.getUserId(), serviceRequest.getUserId())) {
            throw new PermissionDeniedException("Permission Denied, you can manage only your Transactions");

        } else {
            command.execute(repository, transaction);
        }
    }


    public TransactionDataResponse getAllTransactions(TransactionServiceRequest request) {
        List<Transaction> transactions = getListOfAllTransactions(request.getTypeFilter());

        if (isAdmin(request.getUserAuthorities())) {
            return new TransactionDataResponse(transactions);
        }

        var userTransactions = transactions.stream()
                .filter(t -> Objects.equals(request.getUserId(), t.getUserId()))
                .toList();

        return new TransactionDataResponse(userTransactions);
    }


    public List<Transaction> getListOfAllTransactions(String typeFilter) {
        if (typeFilter == null) {
            return repository.findAll();
        }
        if (!typeFilter.matches("^(income|expense)$")) {
            throw new WrongFilterException("type must be 'income' or 'expense'");
        }
        return repository.findAll()
                .stream()
                .filter(t -> t.getType().equals(typeFilter))
                .toList();
    }
}
