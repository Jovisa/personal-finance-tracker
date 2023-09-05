package com.tw.personalfinancetracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TransactionServiceRequest {
    private String userId;
    private List<String> userAuthorities;
    private String typeFilter;
}
