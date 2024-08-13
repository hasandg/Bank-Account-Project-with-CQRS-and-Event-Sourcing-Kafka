package com.hasandag.account.cmd.domain;

import com.hasandag.account.cmd.api.commands.OpenAccountCommand;
import com.hasandag.account.common.events.AccountClosedEvent;
import com.hasandag.account.common.events.AccountOpenedEvent;
import com.hasandag.account.common.events.FundsDepositedEvent;
import com.hasandag.account.common.events.FundsWithdrawnEvent;
import com.hasandag.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    //    private String accountHolder;
    private double balance;
    private boolean active;

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .initialBalance(command.getInitialBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {   // this seen as not called but it called with reflection on AggregateRoot.applyChange()
        this.id = event.getId();
        this.balance = event.getInitialBalance();
        this.active = true;
    }

    public void depositFunds(double amount) {
        if (!active) {
            throw new IllegalStateException("Account is closed");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!active) {
            throw new IllegalStateException("Account is closed");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(-amount)
                .build());
    }

   public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!active) {
            throw new IllegalStateException("Account is already closed");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.active = false;
    }



}
