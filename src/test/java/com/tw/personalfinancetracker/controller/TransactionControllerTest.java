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
import org.springframework.security.core.userdetails.UserDetails;
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
//@ContextConfiguration(classes = {SecurityConfiguration.class, UserDetails.class,})
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public UserDetails userDetails;

    @MockBean
    private TransactionService service;

    @Test
    @WithMockUser(username = "user2", password = "user2")
    public void transactionInfoEndpointTest() throws Exception {
//        when(userDetails.getUsername())
//                .thenReturn("user2");
//
//        when(userDetails.getPassword())
//                .thenReturn("user2");

        when(service.getAllTransactions(any(), any()))
                .thenReturn(TestUtil.generateResponse());

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions", Matchers.hasSize(6)))
                .andExpect(jsonPath("$.summary.totalIncome", Matchers.equalTo(4900.0)))
                .andExpect(jsonPath("$.summary.totalExpense", Matchers.equalTo(2735.0)));
    }

    @Test
    @WithMockUser(username = "1", password = "user2")
    public void transactionInfoWithFilterIncomeTest() throws Exception {
        when(userDetails.getUsername())
                .thenReturn("1");
        when(service.getAllTransactions(userDetails, "income"))
                .thenReturn(TestUtil.generateFilteredResponse());

        mockMvc.perform(get("/transactions")
                        .param("filterByType", "income"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.summary.totalIncome", Matchers.equalTo(4900.0)))
                .andExpect(jsonPath("$.summary.totalExpense", Matchers.equalTo(0.0)));
    }

    @Test
    @WithMockUser(username = "user2", password = "user2")
    public void addNewTransactionEndpointTest() throws Exception {
        final var request = "{\"type\":\"income\",\"amount\": 1.0,\"description\":\"\"}";

        mockMvc.perform(post("/transactions/new")
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

        Mockito.verify(service, times(1)).update(any());
    }

}