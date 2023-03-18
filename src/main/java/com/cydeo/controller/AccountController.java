package com.cydeo.controller;

import com.cydeo.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
