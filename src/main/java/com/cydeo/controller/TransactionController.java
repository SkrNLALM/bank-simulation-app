package com.cydeo.controller;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.services.AccountService;
import com.cydeo.services.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class TransactionController {
    private final AccountService accountService;     //injection of account service with constructor
    private  final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model){

        // What we need to provide to make transfer happen

        // we need to provide empty transaction object
        model.addAttribute("transaction", Transaction.builder().build());

       // we need all accounts to provide them as sender, receiver
        model.addAttribute("accounts", accountService.listAllAccounts());
       // we need list of transactions(last 10)
        model.addAttribute("lastTransactions", transactionService.last10Transactions());

       // once completed make related changes in the make transfer html


        return "transaction/make-transfer";
    }

    //write a post method, that takes transaction object from the method above
    @PostMapping("/transfer")
    public String postMakeTransfer(@ModelAttribute("transaction")Transaction transaction){
        //I have UUID of accounts but I need to provide Account object
        //I nneed to find Accounts based on the ID that I have and us a s a parameter to make transfer
        Account sender =accountService.retrieveById(transaction.getSender());
        Account receiver = accountService.retrieveById(transaction.getReceiver());
        transactionService.makeTransfer(sender,receiver,transaction.getAmount(),new Date(), transaction.getMessage());

        return "redirect:/make-transfer";
    }
    //complete the make transfer and return sma page
}
