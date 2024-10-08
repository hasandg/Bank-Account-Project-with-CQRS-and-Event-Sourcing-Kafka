package com.hasandag.account.cmd.api.commands;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(CloseAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithDrawFundsCommand command);
    void handle(RestoreReadDBCommand command);

}
