package com.hasandag.account.cmd.infrastructure;

import com.hasandag.cqrs.core.events.BaseEvent;
import com.hasandag.cqrs.core.producers.EventProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

    @Autowired
    private KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
