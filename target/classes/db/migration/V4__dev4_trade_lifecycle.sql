-- =====================================================================
-- Wealth Link Platform
-- Dev 4 - Trade Lifecycle module
-- Trade Order | Trade Execution | Settlement
--
-- Depends on V3__dev3_portfolio.sql (portfolio) and
-- V2__dev2_funds_market_data.sql (fund_share_class, currency).
-- =====================================================================

-- ---------------------------------------------------------------------
-- Trade Order
-- Stores the customer's Buy/Sell order request.
-- ---------------------------------------------------------------------

create table trade_order (
    id                   varchar(36) primary key default (gen_random_uuid()),
    portfolio_id         varchar(36) not null references portfolio (id) on delete restrict,
    fund_share_class_id  varchar(36) not null references fund_share_class (id) on delete restrict,
    order_reference      varchar(50) not null unique,
    side                 varchar(10) not null check (side in ('BUY', 'SELL')),
    status               varchar(20) not null check (status in ('PENDING', 'SUBMITTED', 'PARTIALLY_FILLED', 'FILLED', 'CANCELLED', 'REJECTED')),
    requested_quantity   decimal(24, 8) not null check (requested_quantity > 0),
    currency_id          varchar(36) not null references currency (id) on delete restrict,
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
    id                   varchar(36) primary key default (gen_random_uuid()),
    trade_order_id       varchar(36) not null references trade_order (id) on delete restrict,
    execution_reference  varchar(50) not null unique,
    status               varchar(20) not null check (status in ('PENDING', 'CONFIRMED', 'FAILED', 'CANCELLED')),
    executed_quantity    decimal(24, 8) not null check (executed_quantity > 0),
    execution_price      decimal(24, 8) not null check (execution_price > 0),
    gross_amount         decimal(24, 6) not null,
    fee                  decimal(24, 6) not null default 0,
    tax                  decimal(24, 6) not null default 0,
    net_amount           decimal(24, 6) not null,
    currency_id          varchar(36) not null references currency (id) on delete restrict,
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
    id                   varchar(36) primary key default (gen_random_uuid()),
    trade_execution_id   varchar(36) not null references trade_execution (id) on delete restrict,
    settlement_reference varchar(50) not null unique,
    status               varchar(20) not null check (status in ('PENDING', 'IN_PROGRESS', 'SETTLED', 'FAILED')),
    settlement_date      date not null,
    settled_at           timestamp(6),
    currency_id          varchar(36) not null references currency (id) on delete restrict,
    settled_amount       decimal(24, 6) not null,
    failure_reason       varchar(500),
    created_at           timestamp(6) not null default current_timestamp(6),
    updated_at           timestamp(6) not null default current_timestamp(6)
);

create index ix_settlement_trade_execution on settlement (trade_execution_id);
create index ix_settlement_status on settlement (status);
create index ix_settlement_settlement_date on settlement (settlement_date desc);
