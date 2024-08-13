package com.hasandag.account.cmd.controllers;

import com.hasandag.account.cmd.api.commands.RestoreReadDBCommand;
import com.hasandag.account.common.dto.BaseResponse;
import com.hasandag.cqrs.core.infrastructure.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/restoreReadDb")
public class RestoreReadDbController {

    private final Logger logger = LoggerFactory.getLogger(RestoreReadDbController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDb() {
        try {
            commandDispatcher.send(new RestoreReadDBCommand());
            return new ResponseEntity<>(new BaseResponse("Read databbase restore request completed successfully."), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.warn(MessageFormat.format("Client made a bad request - {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to restore read database.";
            logger.error(safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
