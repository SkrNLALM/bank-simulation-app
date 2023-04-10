package com.cydeo.services.impl;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.services.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Component
public class AccountServiceimpl implements AccountService {

    private final AccountRepository accountRepository;  // inject the account repository

    public AccountServiceimpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        //we need to create Account object
        Account account = Account.builder().id(UUID.randomUUID())
                .userId(userId).balance(balance).accountType(accountType).creationDate(creationDate)
                .accountStatus(AccountStatus.ACTIVE).build();
        // save into the database(repository)
        //return the object created
        return accountRepository.save(account);

    }

    @Override
    public List<Account> listAllAccounts() {

        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(UUID id) {
        //find the account abject based on id
     Account account=accountRepository.findById(id);
     // update the accountStatus of the object
        account.setAccountStatus(AccountStatus.DELETED);


    }

    @Override
    public void activateAccount(UUID id) {
        //find the account abject based on id
        Account account=accountRepository.findById(id);
        // update the accountStatus of the object
        account.setAccountStatus(AccountStatus.ACTIVE);


    }

    @Override
    public Account retrieveById(UUID id) {
        return accountRepository.findById(id);
    }
}

