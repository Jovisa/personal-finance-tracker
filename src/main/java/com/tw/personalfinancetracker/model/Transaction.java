package com.tw.personalfinancetracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String type;
    private Double amount;

    public Transaction(String type, Double amount) {
        this.type = type;
        this.amount = amount;
    }
}
