package com.hasandag.account.cmd.api.commands;

import com.hasandag.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand() {
    }

    public CloseAccountCommand(String id) {
        super(id);
    }
}
