package com.hasandag.account.query.infrastructure.handlers;

import com.hasandag.account.common.events.AccountClosedEvent;
import com.hasandag.account.common.events.AccountOpenedEvent;
import com.hasandag.account.common.events.FundsDepositedEvent;
import com.hasandag.account.common.events.FundsWithdrawnEvent;
import com.hasandag.account.query.domain.AccountRepository;
import com.hasandag.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements Eventhandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .createdDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getInitialBalance())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var newBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(newBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var newBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(newBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
