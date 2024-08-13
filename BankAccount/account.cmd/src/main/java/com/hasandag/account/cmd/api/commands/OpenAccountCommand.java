package com.hasandag.account.cmd.api.commands;

import com.hasandag.account.common.dto.AccountType;
import com.hasandag.cqrs.core.commands.BaseCommand;

public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double initialBalance;

    public OpenAccountCommand() {
    }

    public OpenAccountCommand(String id, String accountHolder, AccountType accountType, double initialBalance) {
        super(id);
        this.accountHolder = accountHolder;
        this.accountType = accountType;
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

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
