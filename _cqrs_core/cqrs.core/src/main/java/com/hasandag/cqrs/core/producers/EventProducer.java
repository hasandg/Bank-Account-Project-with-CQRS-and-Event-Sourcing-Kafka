package com.hasandag.cqrs.core.producers;

import com.hasandag.cqrs.core.events.BaseEvent;

public interface EventProducer  {
    void produce(String topic, BaseEvent event);

}
