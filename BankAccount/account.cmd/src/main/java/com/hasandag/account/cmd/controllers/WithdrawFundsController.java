package com.hasandag.account.cmd.controllers;

import com.hasandag.account.cmd.api.commands.DepositFundsCommand;
import com.hasandag.account.cmd.api.commands.WithDrawFundsCommand;
import com.hasandag.account.common.dto.BaseResponse;
import com.hasandag.cqrs.core.exceptions.AggregateNotFoundException;
import com.hasandag.cqrs.core.infrastructure.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {
    private final Logger logger = LoggerFactory.getLogger(WithdrawFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable String id, @RequestBody WithDrawFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Withdraw funds command request completed successfully."), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.warn(MessageFormat.format("Client made a bad request - {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing the request to withdraw funds from bank account with id - {0}", id);
            logger.error(safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
