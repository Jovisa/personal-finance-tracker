package com.tw.personalfinancetracker.model.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize(builder = TransactionRequest.TransactionRequestBuilder.class)
public class TransactionRequest {

    @Pattern(
            regexp = "^(income|expense)$",
            message = "type must be 'income' or 'expense'"
    )
    private String type;

    @Positive(message = "Amount must be a positive number")
    private Double amount;

    private String description;
}
