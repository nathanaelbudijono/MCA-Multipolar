package com.bootcamp.multipolar.bankAccount.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bootcamp.multipolar.bankAccount.domain.Account;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("{'accountHolder.name' : {$regex:?0, $options :'i'}}")
    Optional<Account> findByAccountHolderCaseInsensitive(String name);
}
