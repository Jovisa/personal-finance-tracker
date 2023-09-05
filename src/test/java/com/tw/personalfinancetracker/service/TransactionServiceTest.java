package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.exception.TransactionNotFoundException;
import com.tw.personalfinancetracker.exception.WrongFilterException;
import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.dto.TransactionDataResponse;
import com.tw.personalfinancetracker.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.tw.personalfinancetracker.util.Constants.INCOME;
import static com.tw.personalfinancetracker.util.TestUtil.TRANSACTIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Mockito.when(repository.findAll()).thenReturn(TRANSACTIONS);
        assertEquals(TRANSACTIONS, service.getAllTransactions().getTransactions());
    }

    @Test
    public void serviceWorksWithFilterIncomeTest() {
        Mockito.when(repository.findAll()).thenReturn(TRANSACTIONS);

        TransactionDataResponse response = service.getAllTransactions(INCOME);

        Mockito.verify(repository, times(1)).findAll();
        Assertions.assertEquals(1, response.getTransactions().size());
        Assertions.assertEquals(1L, response.getTransactions().get(0).getId());
        Assertions.assertEquals(INCOME, response.getTransactions().get(0).getType());
    }

    @Test
    public void serviceThrowsExceptionWhenFilterIsInvalidTest() {
        Mockito.when(repository.findAll()).thenReturn(TRANSACTIONS);

        Exception exception = assertThrows(WrongFilterException.class, () ->
            service.getAllTransactions("invalidFilter")
        );

        assertEquals("type must be 'income' or 'expense'", exception.getMessage());
    }

    @Test
    public void serviceCallsDeleteMethodOfRepositoryTest() {
        Mockito.when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(any(Long.class));
        service.deleteTransaction(1L);
        Mockito.verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void deleteThrowsExceptionIfIdNotExist() {
        Mockito.when(repository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(TransactionNotFoundException.class, () ->
            service.deleteTransaction(1L)
        );
        assertEquals("Transaction you were trying to delete doesn't exist", exception.getMessage());
    }

    @Test
    public void serviceCallsRepositoryToUpdateTransaction() {
        Transaction transaction = new Transaction( 1L, 1,  "income", 1.0, "");
        service.update(transaction);
        Mockito.verify(repository, times(1)).save(transaction);
    }

    @Test
    public void serviceCallRpositoryToAddNewTransactionTest() {
        Transaction transaction = new Transaction( 1L, 1, "income", 1.0, "");
        service.save(transaction);
        Mockito.verify(repository, times(1)).save(transaction);
    }

}