package com.tw.personalfinancetracker.service;

import com.tw.personalfinancetracker.exception.TransactionNotFoundException;
import com.tw.personalfinancetracker.exception.WrongFilterException;
import com.tw.personalfinancetracker.mapper.TransactionMapper;
import com.tw.personalfinancetracker.model.entity.Transaction;
import com.tw.personalfinancetracker.model.TransactionServiceRequest;
import com.tw.personalfinancetracker.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static com.tw.personalfinancetracker.util.Constants.INCOME;
import static com.tw.personalfinancetracker.util.Constants.ROLE_ADMIN;
import static com.tw.personalfinancetracker.util.TestUtil.TRANSACTIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class TransactionServiceTest {
    private final TransactionRepository repository = mock(TransactionRepository.class);
    private final TransactionMapper mapper = mock(TransactionMapper.class);
    private final TransactionService service = new TransactionService(repository, mapper);

    private final TransactionServiceRequest serviceRequest = mock(TransactionServiceRequest.class);
    private final Transaction transaction = mock(Transaction.class);

    @BeforeEach
    public void init() {
        when(serviceRequest.getUserId()).thenReturn("1");
        when(serviceRequest.getUserAuthorities()).thenReturn(List.of(ROLE_ADMIN));
        when(serviceRequest.getTypeFilter()).thenReturn(null);
        when(serviceRequest.getTransactionId()).thenReturn(1L);

        when(transaction.getId()).thenReturn(1L);
        when(transaction.getUserId()).thenReturn("1");
        when(transaction.getType()).thenReturn("income");
        when(transaction.getAmount()).thenReturn(1.0);
        when(transaction.getDescription()).thenReturn("");

        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(transaction));
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
        service.deleteTransaction(serviceRequest);
        Mockito.verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void deleteThrowsExceptionIfIdNotExist() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(TransactionNotFoundException.class, () ->
            service.deleteTransaction(serviceRequest)
        );

        assertEquals("Transaction you were trying to delete doesn't exist", exception.getMessage());
    }


    @Test
    public void serviceCallsRepositoryToUpdateTransaction() {
        service.update(serviceRequest);
        Mockito.verify(repository, times(1)).save(any());
    }

    @Test
    public void serviceCallRpositoryToAddNewTransactionTest() {
        service.save(serviceRequest);
        Mockito.verify(repository, times(1)).save(any());
    }

}