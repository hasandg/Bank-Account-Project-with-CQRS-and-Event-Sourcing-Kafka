package com.hasandag.account.common.events;

import com.hasandag.cqrs.core.events.BaseEvent;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
    public AccountClosedEvent() {
    }

    public AccountClosedEvent(String id) {
        super(id, 1);
    }
}
