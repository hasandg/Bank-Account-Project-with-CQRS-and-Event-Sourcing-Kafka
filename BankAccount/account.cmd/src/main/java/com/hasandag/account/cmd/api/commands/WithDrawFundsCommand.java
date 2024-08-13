package com.hasandag.account.cmd.api.commands;

import com.hasandag.cqrs.core.commands.BaseCommand;

public class WithDrawFundsCommand extends BaseCommand {
    private double amount;

    public WithDrawFundsCommand() {
    }

    public WithDrawFundsCommand(String id, double amount) {
        super(id);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
