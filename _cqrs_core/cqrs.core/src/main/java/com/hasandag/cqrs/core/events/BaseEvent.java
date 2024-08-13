package com.hasandag.cqrs.core.events;

import com.hasandag.cqrs.core.messages.Message;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class BaseEvent extends Message {
    private int version;

    public BaseEvent() {
    }

    public BaseEvent(String id, int version) {
        super(id);
        this.version = version;
    }

}
