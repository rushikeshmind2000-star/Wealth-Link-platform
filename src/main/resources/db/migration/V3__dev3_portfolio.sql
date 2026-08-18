-- =====================================================================
-- Wealth Link Platform
-- Dev 3 - Portfolio module
-- Portfolio | Position | PortfolioValuationSnapshot
-- TradeOrder | TradeExecution | Settlement
-- =====================================================================

create table portfolio (
    id               UUID primary key default (gen_random_uuid()),
    account_id       UUID not null references account (id) on delete restrict,
    portfolio_number VARCHAR(255) not null unique,
    portfolio_type   VARCHAR(255) not null check (portfolio_type in ('STANDARD', 'RETIREMENT', 'MARGIN')),
    base_currency_id UUID not null references currency (id) on delete restrict,
    status           VARCHAR(255) not null check (status in ('ACTIVE', 'CLOSED', 'SUSPENDED')),
    opened_at        TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6)
);

create index ix_portfolio_account on portfolio (account_id);

create table portfolio_valuation_snapshot (
    id             UUID primary key default (gen_random_uuid()),
    portfolio_id   UUID not null references portfolio (id) on delete restrict,
    valuation_date date not null,
    total_value    DECIMAL(24,6) not null,
    currency_id    UUID not null references currency (id) on delete restrict,
    created_at     TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6),
    
    constraint uq_portfolio_valuation_date unique (portfolio_id, valuation_date)
);

create table position (
    id                     UUID primary key default (gen_random_uuid()),
    portfolio_id           UUID not null references portfolio (id) on delete restrict,
    fund_share_class_id    UUID not null references fund_share_class (id) on delete restrict,
    position_date          date not null,
    quantity               DECIMAL(24,8) not null check (quantity >= 0),
    average_cost           DECIMAL(24,8) not null,
    cost_basis_currency_id UUID not null references currency (id) on delete restrict,
    market_value           DECIMAL(24,6) not null,
    currency_id            UUID not null references currency (id) on delete restrict,
    status                 VARCHAR(255) not null check (status in ('OPEN', 'CLOSED', 'RECONCILED')),
    computed_at            TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6),
    
    constraint uq_position_portfolio_fund_date unique (portfolio_id, fund_share_class_id, position_date)
);

create index ix_position_portfolio_date on position (portfolio_id, position_date desc);

-- ---------------------------------------------------------------------
-- Trade Order
-- Stores the customer's Buy/Sell order request.
-- ---------------------------------------------------------------------

create table trade_order (
    id                   UUID primary key default (gen_random_uuid()),
    portfolio_id         UUID not null references portfolio (id) on delete restrict,
    fund_share_class_id  UUID not null references fund_share_class (id) on delete restrict,
    order_reference      varchar(50) not null unique,
    side                 varchar(10) not null check (side in ('BUY', 'SELL')),
    status               varchar(20) not null check (status in ('PENDING', 'SUBMITTED', 'PARTIALLY_FILLED', 'FILLED', 'CANCELLED', 'REJECTED')),
    requested_quantity   decimal(24, 8) not null check (requested_quantity > 0),
    currency_id          UUID not null references currency (id) on delete restrict,
    notes                varchar(1000),
    created_at           timestamp(6) not null default current_timestamp(6),
    updated_at           timestamp(6) not null default current_timestamp(6)
);

create index ix_trade_order_portfolio on trade_order (portfolio_id);
create index ix_trade_order_fund_share_class on trade_order (fund_share_class_id);
create index ix_trade_order_status on trade_order (status);

-- ---------------------------------------------------------------------
-- Trade Execution
-- Stores the actual executed quantity, price, fees, tax, etc.
-- ---------------------------------------------------------------------

create table trade_execution (
    id                   UUID primary key default (gen_random_uuid()),
    trade_order_id       UUID not null references trade_order (id) on delete restrict,
    execution_reference  varchar(50) not null unique,
    status               varchar(20) not null check (status in ('PENDING', 'CONFIRMED', 'FAILED', 'CANCELLED')),
    executed_quantity    decimal(24, 8) not null check (executed_quantity > 0),
    execution_price      decimal(24, 8) not null check (execution_price > 0),
    gross_amount         decimal(24, 6) not null,
    fee                  decimal(24, 6) not null default 0,
    tax                  decimal(24, 6) not null default 0,
    net_amount           decimal(24, 6) not null,
    currency_id          UUID not null references currency (id) on delete restrict,
    executed_at          timestamp(6) not null,
    created_at           timestamp(6) not null default current_timestamp(6),
    updated_at           timestamp(6) not null default current_timestamp(6)
);

create index ix_trade_execution_trade_order on trade_execution (trade_order_id);
create index ix_trade_execution_status on trade_execution (status);
create index ix_trade_execution_executed_at on trade_execution (executed_at desc);

-- ---------------------------------------------------------------------
-- Settlement
-- Manages the settlement of the executed trade.
-- ---------------------------------------------------------------------

create table settlement (
    id                   UUID primary key default (gen_random_uuid()),
    trade_execution_id   UUID not null references trade_execution (id) on delete restrict,
    settlement_reference varchar(50) not null unique,
    status               varchar(20) not null check (status in ('PENDING', 'IN_PROGRESS', 'SETTLED', 'FAILED')),
    settlement_date      date not null,
    settled_at           timestamp(6),
    currency_id          UUID not null references currency (id) on delete restrict,
    settled_amount       decimal(24, 6) not null,
    failure_reason       varchar(500),
    created_at           timestamp(6) not null default current_timestamp(6),
    updated_at           timestamp(6) not null default current_timestamp(6)
);

create index ix_settlement_trade_execution on settlement (trade_execution_id);
create index ix_settlement_status on settlement (status);
create index ix_settlement_settlement_date on settlement (settlement_date desc);
