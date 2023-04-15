package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.services.AccountService;
import com.cydeo.services.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

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
    public String postMakeTransfer(@Valid @ModelAttribute("transaction")Transaction transaction, BindingResult bindingResult, Model model){
        //I have UUID of accounts but I need to provide Account object
        //I need to find Accounts based on the ID that I have and us a s a parameter to make transfer

        if(bindingResult.hasErrors()){
            model.addAttribute("accounts", accountService.listAllAccounts());
            return "transaction/make-transfer";

        }
        Account sender =accountService.retrieveById(transaction.getSender());
        Account receiver = accountService.retrieveById(transaction.getReceiver());
        transactionService.makeTransfer(sender,receiver,transaction.getAmount(),new Date(), transaction.getMessage());

        return "redirect:/make-transfer";
    }
    //write a method, that gets the account id from index.html and print on the console
    //(work on index.html and here)
    //transaction/{id}
    //return transaction

    @GetMapping("/transaction/{id}")
    public String getTransactionList(@PathVariable("id") UUID id, Model model){
        //get the list of transactions based on id and return as a model attribute
        //TASK-Complete the method (Service and repository)
        //findTranscationById
        model.addAttribute("transactions", transactionService.findTransactionListById(id));



        System.out.println(id);
        return "/transaction/transactions";

    }

}
