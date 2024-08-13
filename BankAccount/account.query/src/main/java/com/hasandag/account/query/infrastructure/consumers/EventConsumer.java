package com.hasandag.account.query.infrastructure.consumers;

import com.hasandag.cqrs.core.events.BaseEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload BaseEvent event, Acknowledgment ack);

}
