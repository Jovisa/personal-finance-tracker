package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.exception.TransactionNotFoundException;
import com.tw.personalfinancetracker.exception.WrongFilterException;
import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.TransactionServiceRequest;
import com.tw.personalfinancetracker.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.tw.personalfinancetracker.util.Constants.INCOME;
import static com.tw.personalfinancetracker.util.Constants.ROLE_ADMIN;
import static com.tw.personalfinancetracker.util.TestUtil.TRANSACTIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class TransactionServiceTest {
    private final TransactionRepository repository = mock(TransactionRepository.class);

    private final TransactionService service = new TransactionService(repository);

    private final TransactionServiceRequest serviceRequest = mock(TransactionServiceRequest.class);

    @BeforeEach
    public void init() {
        when(serviceRequest.getUserId()).thenReturn("1");
        when(serviceRequest.getUserAuthorities()).thenReturn(List.of(ROLE_ADMIN));
        when(serviceRequest.getTypeFilter()).thenReturn(null);
    }


    @Test
    public void serviceReturnsDataFromRepositoryTest() {
        Mockito.when(repository.findAll()).thenReturn(TRANSACTIONS);
        assertEquals(TRANSACTIONS, service.getAllTransactions(serviceRequest).getTransactions());
    }

    @Test
    public void serviceWorksWithFilterIncomeTest() {
        Mockito.when(repository.findAll()).thenReturn(TRANSACTIONS);
        when(serviceRequest.getTypeFilter()).thenReturn(INCOME);

        var response = service.getAllTransactions(serviceRequest);

        Mockito.verify(repository, times(1)).findAll();
        Assertions.assertEquals(1, response.getTransactions().size());
        Assertions.assertEquals(1L, response.getTransactions().get(0).getId());
        Assertions.assertEquals(INCOME, response.getTransactions().get(0).getType());
    }

    @Test
    public void serviceThrowsExceptionWhenFilterIsInvalidTest() {
        Mockito.when(repository.findAll()).thenReturn(TRANSACTIONS);
        when(serviceRequest.getTypeFilter()).thenReturn("invalidFilter");

        Exception exception = assertThrows(WrongFilterException.class, () ->
            service.getAllTransactions(serviceRequest)
        );

        assertEquals("type must be 'income' or 'expense'", exception.getMessage());
    }

    @Test
    public void serviceCallsDeleteMethodOfRepositoryTest() {
        Mockito.when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        service.deleteTransaction(1L, serviceRequest);
        Mockito.verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void deleteThrowsExceptionIfIdNotExist() {
        Mockito.when(repository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(TransactionNotFoundException.class, () ->
            service.deleteTransaction(eq(1L), serviceRequest)
        );
        assertEquals("Transaction you were trying to delete doesn't exist", exception.getMessage());
    }

    @Test
    public void serviceCallsRepositoryToUpdateTransaction() {
        Transaction transaction = new Transaction( 1L, "1",  "income", 1.0, "");
        when(repository.existsById(1L)).thenReturn(true);
        service.update(transaction, serviceRequest);
        Mockito.verify(repository, times(1)).save(transaction);
    }

    @Test
    public void serviceCallRpositoryToAddNewTransactionTest() {
        Transaction transaction = new Transaction( 1L, "1", "income", 1.0, "");
        service.save(transaction);
        Mockito.verify(repository, times(1)).save(transaction);
    }

}