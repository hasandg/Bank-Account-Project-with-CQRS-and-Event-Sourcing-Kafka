package com.hasandag.cqrs.core.commands;

import com.hasandag.cqrs.core.messages.Message;

public abstract class BaseCommand extends Message {
    public BaseCommand() {
    }

    public BaseCommand(String id) {
        super(id);
    }
}
