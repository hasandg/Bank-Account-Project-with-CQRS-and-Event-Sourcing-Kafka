package com.hasandag.account.query.infrastructure;

import com.hasandag.cqrs.core.domain.BaseEntity;
import com.hasandag.cqrs.core.infrastructure.QueryDispatcher;
import com.hasandag.cqrs.core.queries.BaseQuery;
import com.hasandag.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> queryType, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(queryType, c -> new LinkedList<>());
        //var handlers = routes.computeIfAbsent(queryType, c -> List.of());
        handlers.add(handler);


    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.isEmpty()){
            throw new RuntimeException("No query handler registered for query: " + query.getClass().getName());
        }
        if (handlers.size() > 1){
            throw new RuntimeException("Multiple query handlers registered for query: " + query.getClass().getName());
        }
        return handlers.get(0).handle(query);
    }
}
