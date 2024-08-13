package com.hasandag.account.query.api.controllers;

import com.hasandag.account.query.api.dto.AccountLookupResponse;
import com.hasandag.account.query.api.dto.EqualityType;
import com.hasandag.account.query.api.queries.FindAccountByHolderQuery;
import com.hasandag.account.query.api.queries.FindAccountByIdQuery;
import com.hasandag.account.query.api.queries.FindAccountsWithBalanceQuery;
import com.hasandag.account.query.api.queries.FindAllAccountsQuery;
import com.hasandag.account.query.domain.BankAccount;
import com.hasandag.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {
    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
            if (accounts == null || accounts.isEmpty()) {
                var safeErrorMessage = "No accounts found.";
                logger.warning(safeErrorMessage);
                return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.NOT_FOUND);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank account(s).", accounts.size()))
                    .build();
            return new ResponseEntity<>(new AccountLookupResponse(accounts), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete the request to retrieve all accounts.";
            logger.severe(safeErrorMessage);
            //return ResponseEntity.status(500).body(new AccountLookupResponse(safeErrorMessage));
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if (accounts == null || accounts.isEmpty()) {
                var safeErrorMessage = "No accounts found.";
                logger.warning(safeErrorMessage);
                return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.NOT_FOUND);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account!")
                    .build();
            return new ResponseEntity<>(new AccountLookupResponse(accounts), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete to account by ID request!";
            logger.severe(safeErrorMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value = "accountHolder") String accountHolder) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if (accounts == null || accounts.isEmpty()) {
                var safeErrorMessage = "No accounts found.";
                logger.warning(safeErrorMessage);
                return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.NOT_FOUND);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account!")
                    .build();
            return new ResponseEntity<>(new AccountLookupResponse(accounts), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete to account by holder request!";
            logger.severe(safeErrorMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                       @PathVariable(value = "balance") double balance) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountsWithBalanceQuery(equalityType, balance));
            if (accounts == null || accounts.isEmpty()) {
                var safeErrorMessage = "No accounts found.";
                logger.warning(safeErrorMessage);
                return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.NOT_FOUND);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank account(s).", accounts.size()))
                    .build();
            return new ResponseEntity<>(new AccountLookupResponse(accounts), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete to accounts with balance request!";
            logger.severe(safeErrorMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
