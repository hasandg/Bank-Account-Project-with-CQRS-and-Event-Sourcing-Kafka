package com.hasandag.account.cmd.infrastructure;

import com.hasandag.cqrs.core.commands.BaseCommand;
import com.hasandag.cqrs.core.commands.CommandHandlerMethod;
import com.hasandag.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispathcer implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> commandType, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(commandType, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.isEmpty()){
            throw new RuntimeException("No handler found for command: " + command.getClass().getName());
        }
        if (handlers.size() > 1){
            throw new RuntimeException("Multiple handlers found for command: " + command.getClass().getName());
        }
        handlers.get(0).handle(command);
    }
}
