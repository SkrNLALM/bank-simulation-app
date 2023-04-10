package com.cydeo;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.services.AccountService;
import com.cydeo.services.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {

        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);


        //get account and transaction service beans
      AccountService accountService=container.getBean(AccountService.class);
      TransactionService transactionService=container.getBean(TransactionService.class);

      //create 2 account sender and receiver
//        Account sender=accountService.createNewAccount(BigDecimal.valueOf(170), new Date(), AccountType.CHECKING, 1L);
//        Account receiver2=accountService.createNewAccount(BigDecimal.valueOf(150), new Date(), AccountType.SAVING, 2L);
//        Account sender2=accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 123L);
//        Account receiver3=accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.SAVING, 133L);
//        accountService.listAllAccounts().forEach(System.out::println);
//
//        transactionService.makeTransfer(sender,receiver, new BigDecimal(70), new Date(), "Transaction 1");
//        System.out.println(transactionService.findAllTransaction().get(0));


    }

}
