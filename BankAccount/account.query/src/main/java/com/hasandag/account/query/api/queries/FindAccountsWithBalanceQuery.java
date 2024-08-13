package com.hasandag.account.query.api.queries;

import com.hasandag.account.query.api.dto.EqualityType;
import com.hasandag.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {
    private EqualityType equaityType;
    private double balance;
}
