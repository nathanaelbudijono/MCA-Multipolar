package com.bootcamp.multipolar.bankAccount.controller;

import java.util.*;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.multipolar.bankAccount.domain.Account;
import com.bootcamp.multipolar.bankAccount.dto.ErrorMessage;
import com.bootcamp.multipolar.bankAccount.service.AccountService;

@RestController
@RequestMapping("/api/bankAccount")

public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllBankAccount() {
        return accountService.getAllBankAccount();
    }

    @PostMapping
    public ResponseEntity<?> createBankAccount(@Valid @RequestBody Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ErrorMessage> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode("VALIDATION_ERROR");
                errorMessage.setMessage(error.getDefaultMessage());
                validationErrors.add(errorMessage);
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        Account createdAccount = accountService.createBankAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBankAccount(@PathVariable String id, @Valid @RequestBody Account account,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ErrorMessage> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode("VALIDATION_ERROR");
                errorMessage.setMessage(error.getDefaultMessage());
                validationErrors.add(errorMessage);
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        Account updatedAccount = accountService.updateBankAccount(id, account);
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable String id) {
        Optional<Account> accountId = accountService.getBankAccountById(id);
        return accountId.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable String id) {
        accountService.deleteBankAccountById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<?> getBankAccountByAccountNumber(@PathVariable String accountNumber) {
        Optional<Account> getAccountNumber = accountService.getByAccountNumber(accountNumber);
        return getAccountNumber.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getBankAccountByNameCaseInsensitive(@PathVariable String name) {
        Optional<Account> getName = accountService.findByAccountHolderCaseInsensitive(name);
        return getName.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
