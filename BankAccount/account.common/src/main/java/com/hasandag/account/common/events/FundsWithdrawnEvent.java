package com.hasandag.account.common.events;

import com.hasandag.cqrs.core.events.BaseEvent;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class FundsWithdrawnEvent extends BaseEvent {
    private double amount;

    public FundsWithdrawnEvent() {
    }

    public FundsWithdrawnEvent(String id, double amount) {
        super(id,  1);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
