package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller

//@RequestMapping("account")
public class AccountController {

        private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndex (Model model){

        model.addAttribute("accountList", accountService.listAllAccounts());
                return "account/index";
    }

    @GetMapping("/create-form")
    public String getCreateForm (Model model){

       // model.addAttribute("accountList", accountService.listAllAccounts());
        //empty account object provided by the following
        model.addAttribute("account", Account.builder().build());
        //account type enum needs to fill dropdown
        model.addAttribute("accountTypes", AccountType.values());
        List<AccountType> accounttypeList= Arrays.asList(AccountType.SAVING,AccountType.CHECKING);
        model.addAttribute("accountTypeList", accounttypeList);

        return "account/create-account";
   }

    //create method to capture information from UI
    // print them on the console
    // trigger createAccount method, create the account based on user input
    @PostMapping("/create")
    public String createAccount(@ModelAttribute ("account") Account account){

        System.out.println(account);
        accountService.createNewAccount(account.getBalance(), new Date(), account.getAccountType(), account.getUserId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") UUID id){

        accountService.deleteAccount(id);

        System.out.println(id);
        return "redirect:/index";

    }
    @GetMapping("/activate/{id}")
    public String activeAccount(@PathVariable("id") UUID id){

        accountService.activateAccount(id);

        System.out.println(id);
        return "redirect:/index";

    }

}
