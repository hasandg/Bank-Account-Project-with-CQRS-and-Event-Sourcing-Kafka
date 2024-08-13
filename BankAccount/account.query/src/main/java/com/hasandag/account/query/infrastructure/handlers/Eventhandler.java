package com.hasandag.account.query.infrastructure.handlers;

import com.hasandag.account.common.events.AccountClosedEvent;
import com.hasandag.account.common.events.AccountOpenedEvent;
import com.hasandag.account.common.events.FundsDepositedEvent;
import com.hasandag.account.common.events.FundsWithdrawnEvent;

public interface Eventhandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
