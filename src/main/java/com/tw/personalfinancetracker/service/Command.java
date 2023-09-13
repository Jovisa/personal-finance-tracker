package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.model.entity.Transaction;
import com.tw.personalfinancetracker.repository.TransactionRepository;

@FunctionalInterface
public interface Command {
    void execute(TransactionRepository repository, Transaction transaction);
}
