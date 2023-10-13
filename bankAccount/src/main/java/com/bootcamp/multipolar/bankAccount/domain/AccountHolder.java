package com.bootcamp.multipolar.bankAccount.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class AccountHolder {
    private String nik;
    private String name;
    private String address;
}
