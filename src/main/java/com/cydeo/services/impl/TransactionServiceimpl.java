package com.cydeo.services.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.services.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Component
public class TransactionServiceimpl implements TransactionService {

    @Value("{$under_Construction}")
    private  boolean underConstruction;


    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceimpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

       @Override
       public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
       /*
       -if the sender or receiver is null?
       -if sender and receiver is the same account?
       -if sender has enough balance ?
       -if both account are checking, if not one of them saving, it needs to be same userID

       */
           if(!underConstruction){
       validateAccount(sender, receiver);
       checkAccountOwnership(sender, receiver);
       executeBalanceAndUpdateIsRequired(amount,sender,receiver);

    /*
    * after all validations are completed, and money is transferred, we need to create Transaction object and save/return it
    *
    *
    *
   */

           Transaction transaction=Transaction.builder().amount(amount).sender(sender.getId())
                   .receiver(receiver.getId()).creationDate(creationDate).message(message).build();

       return transactionRepository.save(transaction);
    }else {
           throw new UnderConstructionException("App is under construction, try again later");
           }
       }

    private void executeBalanceAndUpdateIsRequired(BigDecimal amount, Account sender, Account receiver) {
        if(checkSenderBalance(sender,amount)){
            //make balance transfer between sender and receiver
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

          }else{
            //throw BalanceNotSufficientException
            throw new BalanceNotSufficientException("Balance is not enough for this transfer.");

        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        //verify sender has enough balance to send

        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)>=0;
    }

    private void checkAccountOwnership(Account sender, Account receiver)  {
        //write an if statement that checks if one of the account is saving
        // and user or receiver is not the same, throw AccountOwnershipException

        if((sender.getAccountType().equals(AccountType.SAVING)||receiver.getAccountType().equals(AccountType.SAVING))
            &&!sender.getUserId().equals(receiver.getUserId())){
        throw new AccountOwnershipException ("Since you are using a saving account, the sender and receiveruserId must be the same");

        }
    }

    private void validateAccount(Account sender, Account receiver) {
       /*
            -if any of the account is null
            -if account ids are the same(same account)
            -if the accounts exist in the database(repository)
         */

       if(sender==null||receiver==null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
       if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver");
        }
       //verify if we have sender and receiver in the database
        findAccountById(sender.getId());
        findAccountById(receiver.getId());
        }
       private void findAccountById(UUID id) {
        accountRepository.findById(id);
        }
       @Override
       public List<Transaction> findAllTransaction() {

       return transactionRepository.findAll();
    }
}
