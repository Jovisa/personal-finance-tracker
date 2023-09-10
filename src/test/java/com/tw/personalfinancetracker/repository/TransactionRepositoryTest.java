package com.tw.personalfinancetracker.repository;

import com.tw.personalfinancetracker.model.Transaction;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;


    @Test
    @Transactional
    public void repositoryCreatesAndGetsEntitiesTest() {
        repository.save(new Transaction("1", "income", 1.0, ""));
        repository.save(new Transaction("1", "expense", 2.0, ""));

        List<Transaction> transactions = repository.findAll();

        transactions.forEach(System.out::println);

        assertEquals(2, transactions.size());
        assertEquals("income", transactions.get(0).getType());
    }

    @Test
    @Transactional
    public void deleteTransactionByIdTest() {
        Transaction incomeTransaction = repository.save(new Transaction("1", "income", 1.0, ""));
        Transaction expenseTransaction = repository.save(new Transaction("1", "expense", 2.0, ""));

        List<Transaction> transactions = repository.findAll();

        assertEquals(2, transactions.size());
        assertEquals("income", transactions.get(0).getType());

        repository.deleteById(incomeTransaction.getId());
        transactions = repository.findAll();
        assertEquals(1, transactions.size());
        assertEquals("expense", transactions.get(0).getType());
    }

    @Test
    @Transactional
    public void updateTransactionTest() {
        Transaction incomeTransaction = repository.save(new Transaction("1", "income", 1.0, "income description"));
        Transaction expenseTransaction = repository.save(new Transaction("1", "expense", 2.0, ""));

        List<Transaction> transactions = repository.findAll();
        assertEquals(2, transactions.size());

        incomeTransaction.setAmount(10.0);
        incomeTransaction.setDescription("new descrption");

        repository.save(incomeTransaction);
        Transaction updatedIncomeTransaction = repository.findById(incomeTransaction.getId())
                .orElse(new Transaction());

        assertEquals(10.0, updatedIncomeTransaction.getAmount());
        assertEquals("new descrption", updatedIncomeTransaction.getDescription());
    }

}