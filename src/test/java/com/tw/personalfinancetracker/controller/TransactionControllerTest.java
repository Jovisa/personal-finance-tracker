package com.tw.personalfinancetracker.controller;

import com.tw.personalfinancetracker.model.Transaction;
import com.tw.personalfinancetracker.model.dto.SummaryFactory;
import com.tw.personalfinancetracker.model.dto.TransactionDataResponse;
import com.tw.personalfinancetracker.service.TransactionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.tw.personalfinancetracker.util.TestUtil.generateResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Test
    public void transactionInfoEndpointTest() throws Exception {

        var response = generateResponse();
        when(service.getAllTransactions()).thenReturn(response);

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions", Matchers.hasSize(6)))
                .andExpect(jsonPath("$.summary.totalIncome", Matchers.equalTo(4900.0)))
                .andExpect(jsonPath("$.summary.totalExpense", Matchers.equalTo(2735.0)));
    }

    @Test
    public void transactionInfoWithFilterIncomeTest() throws Exception {

        var response = generateFilteredResponse();
        when(service.getAllTransactions("income")).thenReturn(response);

        mockMvc.perform(get("/transactions")
                        .param("filterByType", "income"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.summary.totalIncome", Matchers.equalTo(4900.0)))
                .andExpect(jsonPath("$.summary.totalExpense", Matchers.equalTo(0.0)));
    }

    @Test
    public void addNewTransactionEndpointTest() throws Exception {
        final var request = "{\"type\":\"income\",\"amount\": 1.0,\"description\":\"\"}";

        mockMvc.perform(post("/transactions/new")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isOk());

        Mockito.verify(service, times(1)).save(any());
    }

    @Test
    public void updateTransactionEndpointTest() throws Exception {
        final var request = "{\"type\":\"income\",\"amount\": 1.0,\"description\":\"\"}";

        mockMvc.perform(put("/transactions/1")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isOk());

        Mockito.verify(service, times(1)).update(any());
    }


    private TransactionDataResponse generateFilteredResponse() {
        final List<Transaction> transactions = List.of(
                new Transaction( 1L, "income", 1000.0, ""),
                new Transaction( 2L, "income", 3400.0, ""),
                new Transaction( 3L, "income", 500.00, "")
        );

        return TransactionDataResponse.builder()
                .summary(SummaryFactory.buildSummary(transactions))
                .transactions(transactions)
                .build();
    }


}