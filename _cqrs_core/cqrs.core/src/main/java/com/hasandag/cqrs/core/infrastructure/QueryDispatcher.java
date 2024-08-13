package com.hasandag.cqrs.core.infrastructure;

import com.hasandag.cqrs.core.domain.BaseEntity;
import com.hasandag.cqrs.core.queries.BaseQuery;
import com.hasandag.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> queryType, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);


}
