package com.tw.personalfinancetracker.controller;

import com.tw.personalfinancetracker.service.TransactionService;
import com.tw.personalfinancetracker.util.TestUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Test
    @WithMockUser(username = "user2", password = "user2")
    public void transactionInfoEndpointTest() throws Exception {
        when(service.getAllTransactions(any()))
                .thenReturn(TestUtil.generateResponse());

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions", Matchers.hasSize(6)))
                .andExpect(jsonPath("$.summary.totalIncome", Matchers.equalTo(4900.0)))
                .andExpect(jsonPath("$.summary.totalExpense", Matchers.equalTo(2735.0)));

//        verify(service,times(1))
//                .getAllTransactions(any(), eq(null));
    }

    @Test
    @WithMockUser(username = "user2", password = "user2")
    public void transactionInfoWithFilterTest() throws Exception {

        when(service.getAllTransactions(any()))
                .thenReturn(TestUtil.generateFilteredResponse());

        mockMvc.perform(get("/transactions")
                        .param("typeFilter", "income"))
                .andExpect(status().isOk());

//        verify(service,times(1))
//                .getAllTransactions(any(), eq("income"));
    }

    @Test
    @WithMockUser(username = "user2", password = "user2")
    public void addNewTransactionEndpointTest() throws Exception {
        final var request = "{\"type\":\"income\",\"amount\": 1.0,\"description\":\"\"}";

        mockMvc.perform(post("/transactions")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isOk());

        Mockito.verify(service, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "user2", password = "user2")
    public void updateTransactionEndpointTest() throws Exception {
        final var request = "{\"type\":\"income\",\"amount\": 1.0,\"description\":\"\"}";

        mockMvc.perform(put("/transactions/1")
                        .contentType("application/json")
                        .content(request)
                )
                .andExpect(status().isOk());

        Mockito.verify(service, times(1)).update(any(), any());
    }

}