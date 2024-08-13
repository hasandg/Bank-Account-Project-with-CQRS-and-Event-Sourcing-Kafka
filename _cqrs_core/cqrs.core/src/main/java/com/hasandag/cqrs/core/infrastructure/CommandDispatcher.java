package com.hasandag.cqrs.core.infrastructure;

import com.hasandag.cqrs.core.commands.BaseCommand;
import com.hasandag.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> commandType, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
