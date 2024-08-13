package com.hasandag.cqrs.core.infrastructure;

import com.hasandag.cqrs.core.events.BaseEvent;
import com.hasandag.cqrs.core.events.EventModel;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);
    List<String> getAggregateIds();
}
