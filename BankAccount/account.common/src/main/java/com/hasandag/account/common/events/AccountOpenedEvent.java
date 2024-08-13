package com.hasandag.account.common.events;

import com.hasandag.account.common.dto.AccountType;
import com.hasandag.cqrs.core.events.BaseEvent;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double initialBalance;

    public AccountOpenedEvent() {
    }

    public AccountOpenedEvent(String id, String accountHolder, AccountType accountType, Date createdDate, double initialBalance) {
        super(id, 1);
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.createdDate = createdDate;
        this.initialBalance = initialBalance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
