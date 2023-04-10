package com.cydeo.services;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<Account> listAllAccounts();

    void deleteAccount(UUID id);

    void activateAccount(UUID id);

    Account retrieveById(UUID id);

}
