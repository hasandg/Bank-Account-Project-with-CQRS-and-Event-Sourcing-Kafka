package com.hasandag.account.query.infrastructure.consumers;

import com.hasandag.account.common.events.AccountClosedEvent;
import com.hasandag.account.common.events.AccountOpenedEvent;
import com.hasandag.account.common.events.FundsDepositedEvent;
import com.hasandag.account.common.events.FundsWithdrawnEvent;
import com.hasandag.account.query.infrastructure.handlers.Eventhandler;
import com.hasandag.cqrs.core.events.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer{

    @Autowired
    private Eventhandler eventHandler;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(BaseEvent event, Acknowledgment ack) {
        try {
            var eventHandlerMethod = eventHandler.getClass().getDeclaredMethod("on", event.getClass());
            eventHandlerMethod.setAccessible(true);
            eventHandlerMethod.invoke(eventHandler, event);
            ack.acknowledge();
        } catch (Exception e) {
            throw new RuntimeException("Error while consuming event", e);
        }
    }
}
