package com.hasandag.account.cmd.infrastructure;

import com.hasandag.account.cmd.domain.AccountAggregate;
import com.hasandag.account.cmd.domain.EventStoreRepository;
import com.hasandag.cqrs.core.events.BaseEvent;
import com.hasandag.cqrs.core.events.EventModel;
import com.hasandag.cqrs.core.exceptions.AggregateNotFoundException;
import com.hasandag.cqrs.core.exceptions.ConcurrencyException;
import com.hasandag.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;
    @Autowired
    private AccountEventProducer accountEventProducer;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateId(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (BaseEvent event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .aggregateId(aggregateId)
                    .aggregateType(AccountAggregate.class.getName())
                    .version(version)
                    .eventType(event.getClass().getName())
                    .eventData(event)
                    .build();
            EventModel persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                accountEventProducer.produce(topic, event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateId(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account id provided");
        }
        return eventStream.stream().map(EventModel::getEventData).toList();
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if (eventStream == null || eventStream.isEmpty()) {
            throw new IllegalStateException("Could not retrieve event stream from the event store!");
        }
        return eventStream.stream().map(EventModel::getAggregateId).distinct().toList();
    }


}
