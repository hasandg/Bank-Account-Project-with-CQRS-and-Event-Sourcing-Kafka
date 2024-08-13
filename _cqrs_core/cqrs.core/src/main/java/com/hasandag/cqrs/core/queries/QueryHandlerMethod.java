package com.hasandag.cqrs.core.queries;

import com.hasandag.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T query);
}
