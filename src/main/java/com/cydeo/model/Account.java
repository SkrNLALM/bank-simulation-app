package com.cydeo.model;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Data
@Builder       //with builder you have custom constructor
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private UUID id;    //account number

    @NotNull
    @Positive
    private BigDecimal balance;   //for bank app big decimal is safe
    @NotNull
    private AccountType accountType;
    private Date creationDate;
    @NotNull
    private  Long userId;
    private AccountStatus accountStatus;






}
