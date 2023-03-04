package com.cydeo.model;

import com.cydeo.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Data
@Builder       //with builder you have custom constructor
public class Account {

    private UUID id;    //account number
    private BigDecimal balance;   //for bank app big decimal is safe
    private AccountType accountType;
    private Date date;
    private  Long userId;





}
