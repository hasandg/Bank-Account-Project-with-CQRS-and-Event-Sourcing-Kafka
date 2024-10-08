package com.hasandag.account.query.api.queries;

import com.hasandag.account.query.api.dto.EqualityType;
import com.hasandag.account.query.domain.AccountRepository;
import com.hasandag.account.query.domain.BankAccount;
import com.hasandag.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountQueryHandler implements QueryHandler {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> accounts = new ArrayList<>();
        bankAccounts.forEach(accounts::add);
        return accounts;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        //Iterable<BankAccount> allById = accountRepository.findAllById(List.of(query.getId()));
        var bankAccount = accountRepository.findById(query.getId());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> accounts = new ArrayList<>();
        accounts.add(bankAccount.get());
        return accounts;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> accounts = new ArrayList<>();
        accounts.add(bankAccount.get());
        return accounts;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {
        List<BaseEntity> bankAccounts = query.getEquaityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
        return bankAccounts;
    }
}
