package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@SpringBootTest
class TransactionServiceTest {

    @MockBean
    private TransactionRepository repository;

    @Autowired
    private TransactionService service;


    @Test
    public void serviceReturnsDataFromRepositoryTest() {

        List<Transaction> transactions = List.of(
                new Transaction( 1L, "income", 1.0, ""),
                new Transaction(2L, "expense", 2.0, "")
        );

        Mockito.when(repository.findAll()).thenReturn(transactions);
        assertEquals(transactions, service.getAllTransactions().getTransactions());
    }

    @Test
    public void serviceCallsDeleteMethodOfRepositoryTest() {
        Mockito.when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(any(Long.class));
        service.deleteTransaction(1L);
        Mockito.verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void serviceCallsRepositoryToUpdateTransaction() {
        Transaction transaction = new Transaction( 1L, "income", 1.0, "");
        service.update(transaction);
        Mockito.verify(repository, times(1)).save(transaction);
    }

    @Test
    public void serviceCallRpositoryToAddNewTransactionTest() {
        Transaction transaction = new Transaction( 1L, "income", 1.0, "");
        service.save(transaction);
        Mockito.verify(repository, times(1)).save(transaction);
    }

}