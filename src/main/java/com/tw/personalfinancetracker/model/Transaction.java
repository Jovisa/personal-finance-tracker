package com.tw.personalfinancetracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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

    @Pattern(
            regexp = "^(income|expense)$",
            message = "type must be 'income' or 'expense'"
    )
    private String type;

    @Positive(message = "Amount must be a positive number")
    private Double amount;

    private String description;

    public Transaction(String type, Double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }
}
