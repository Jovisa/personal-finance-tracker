package com.tw.personalfinancetracker.util;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.repository.TransactionsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final TransactionsRepository repository;

    public Runner(TransactionsRepository repository) {
        this.repository = repository;
    }


    @Override
    public void run(String... args) {
        repository.save(new Transaction("bla", 1.0, ""));
        repository.save(new Transaction("truc", 2.0, ""));

        List<Transaction> transactions = repository.findAll();

        transactions.forEach(System.out::println);
    }
}
