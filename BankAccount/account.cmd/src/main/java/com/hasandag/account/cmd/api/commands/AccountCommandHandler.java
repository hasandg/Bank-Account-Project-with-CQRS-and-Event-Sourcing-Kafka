package com.hasandag.account.cmd.api.commands;

import com.hasandag.account.cmd.domain.AccountAggregate;
import com.hasandag.cqrs.core.hadlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {

    @Autowired
    private EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);  // It's not calling aggregate's method like openAcount, its calling constructor with command info
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithDrawFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        if(aggregate.getBalance() < command.getAmount()) {
            throw new IllegalStateException("Withdrawn declined due to insufficient funds");
        }
        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(RestoreReadDBCommand command) { eventSourcingHandler.republishEvents();   }

}
