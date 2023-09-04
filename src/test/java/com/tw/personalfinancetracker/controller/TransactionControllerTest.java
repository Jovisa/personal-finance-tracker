//package com.tw.personalfinancetracker.controller;
//
//import com.tw.personalfinancetracker.model.Transaction;
//import com.tw.personalfinancetracker.service.TransactionService;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(TransactionController.class)
//class TransactionControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TransactionService service;
//
//    @Test
//    public void transactionInfoEndpointTest() throws Exception {
//        final List<Transaction> response = List.of(
//                new Transaction( 1L, "income", 1.0, ""),
//                new Transaction(2L, "expense", 2.0, "")
//        );
//        when(service.getAllTransactions()).thenReturn(response);
//
//        mockMvc.perform(get("/transactions"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize(2)))
//                .andExpect(jsonPath("$[0].type", Matchers.equalTo("income")));
//    }
//
//    @Test
//    public void addNewTransactionEndpointTest() throws Exception {
//        final var request = "{\"type\":\"income\",\"amount\": 1.0,\"description\":\"\"}";
//
//        mockMvc.perform(post("/transactions/new")
//                        .contentType("application/json")
//                        .content(request))
//                .andExpect(status().isOk());
//
//        Mockito.verify(service, times(1)).save(any());
//    }
//
//    @Test
//    public void updateTransactionEndpointTest() throws Exception {
//        final var request = "{\"type\":\"income\",\"amount\": 1.0,\"description\":\"\"}";
//
//        mockMvc.perform(put("/transactions/1")
//                        .contentType("application/json")
//                        .content(request))
//                .andExpect(status().isOk());
//
//        Mockito.verify(service, times(1)).update(any());
//    }
//
//}