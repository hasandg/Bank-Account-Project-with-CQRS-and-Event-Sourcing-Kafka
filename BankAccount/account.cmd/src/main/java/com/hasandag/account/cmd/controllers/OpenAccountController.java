package com.hasandag.account.cmd.controllers;

import com.hasandag.account.cmd.api.commands.OpenAccountCommand;
import com.hasandag.account.cmd.api.dto.OpenAccountResponse;
import com.hasandag.account.common.dto.BaseResponse;
import com.hasandag.cqrs.core.infrastructure.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/open-account")
public class OpenAccountController {
    private final Logger logger = LoggerFactory.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @RequestMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account oppening request completed successfully.", id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.warn(MessageFormat.format("Client made a bad request - {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing the request to open a bank account for id - {0}", id);
            logger.error(safeErrorMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage,id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
