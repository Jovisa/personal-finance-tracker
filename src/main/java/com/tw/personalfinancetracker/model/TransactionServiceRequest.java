package com.tw.personalfinancetracker.model;

import com.tw.personalfinancetracker.model.dto.request.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TransactionServiceRequest {
    private String userId;
    private List<String> userAuthorities;
    private String typeFilter;
    private TransactionRequest request;
    private Long transactionId;

    public TransactionServiceRequest(
            TransactionRequest request,
            Long transactionId,
            UserDetails user
    ) {
        this.userId = user.getUsername();
        this.userAuthorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        this.request = request;
        this.transactionId = transactionId;
    }

    public TransactionServiceRequest(UserDetails user, String typeFilter) {
        this.userId = user.getUsername();
        this.typeFilter = typeFilter;
        this.userAuthorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public TransactionServiceRequest(UserDetails user, Long transactionId) {
        this.userId = user.getUsername();
        userAuthorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        this.transactionId = transactionId;
    }

}
