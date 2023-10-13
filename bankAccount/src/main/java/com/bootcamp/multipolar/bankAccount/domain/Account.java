package com.bootcamp.multipolar.bankAccount.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;

@Document(collection = "bankAccount")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Account implements Serializable {
    @Id
    private String id;
    @NotEmpty(message = "Account Number is required")
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private AccountHolder accountHolder;
    private Double balance;
    private LocalDateTime openingDateTime = LocalDateTime.now();
    private LocalDate closingDate;
}
