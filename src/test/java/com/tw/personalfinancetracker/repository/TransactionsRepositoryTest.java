package com.tw.personalfinancetracker.repository;

import com.tw.personalfinancetracker.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionsRepositoryTest {

    @Autowired
    private TransactionsRepository repository;


    @BeforeEach
    public void clearDb() {
        repository.deleteAll();
    }

    @Test
    public void repositoryCreatesAndGetsEntitiesTest() {
        repository.save(new Transaction("income", 1.0));
        repository.save(new Transaction("expense", 2.0));

        List<Transaction> transactions = repository.findAll();

        transactions.forEach(System.out::println);

        assertEquals(2, transactions.size());
        assertEquals("income", transactions.get(0).getType());
    }

}