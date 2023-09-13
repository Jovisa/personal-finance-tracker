package com.tw.personalfinancetracker.model;

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

    public TransactionServiceRequest(UserDetails user, String typeFilter) {
        this.userId = user.getUsername();
        this.typeFilter = typeFilter;
        userAuthorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public TransactionServiceRequest(UserDetails user) {
        this.userId = user.getUsername();
        userAuthorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

}
