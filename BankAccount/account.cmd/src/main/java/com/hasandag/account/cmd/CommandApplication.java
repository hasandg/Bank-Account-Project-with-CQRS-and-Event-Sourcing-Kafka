package com.hasandag.account.cmd;

import com.hasandag.account.cmd.api.commands.*;
import com.hasandag.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(OpenAccountCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(WithDrawFundsCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class,commandHandler::handle);
		commandDispatcher.registerHandler(RestoreReadDBCommand.class,commandHandler::handle);
	}

}
