package com.bootcamp.multipolar.bankAccount.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.multipolar.bankAccount.domain.Account;
import com.bootcamp.multipolar.bankAccount.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllBankAccount() {
        return accountRepository.findAll();
    }

    public Optional<Account> getBankAccountById(String id) {
        return accountRepository.findById(id);
    }

    public void deleteBankAccountById(String id) {
        accountRepository.deleteById(id);
    }

    public Account createBankAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Account updateBankAccount(String id, Account account) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            account.setId(id);
            return accountRepository.save(account);
        } else {
            return null;
        }
    }

    public Optional<Account> findByAccountHolderCaseInsensitive(String name) {
        return accountRepository.findByAccountHolderCaseInsensitive(name);
    }
}
