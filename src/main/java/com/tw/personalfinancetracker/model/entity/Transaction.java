package com.tw.personalfinancetracker.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String userId;
    private String type;
    private Double amount;
    private String description;

    public Transaction(String userId, String type, Double amount, String description) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }
}
