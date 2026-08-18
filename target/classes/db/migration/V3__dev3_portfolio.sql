-- =====================================================================
-- Wealth Link Platform
-- Dev 3 — Portfolio & Positions module
-- =====================================================================

create table portfolio (
    id               varchar(36) primary key default (gen_random_uuid()),
    account_id       varchar(36) not null references account (id) on delete restrict,
    portfolio_number VARCHAR(255) not null unique,
    portfolio_type   VARCHAR(255) not null check (portfolio_type in ('STANDARD', 'RETIREMENT', 'MARGIN')),
    base_currency_id varchar(36) not null references currency (id) on delete restrict,
    status           VARCHAR(255) not null check (status in ('ACTIVE', 'CLOSED', 'SUSPENDED')),
    opened_at        TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6)
);

create index ix_portfolio_account on portfolio (account_id);

create table portfolio_valuation_snapshot (
    id             varchar(36) primary key default (gen_random_uuid()),
    portfolio_id   varchar(36) not null references portfolio (id) on delete restrict,
    valuation_date date not null,
    total_value    DECIMAL(24,6) not null,
    currency_id    varchar(36) not null references currency (id) on delete restrict,
    created_at     TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6),
    
    constraint uq_portfolio_valuation_date unique (portfolio_id, valuation_date)
);

create table position (
    id                     varchar(36) primary key default (gen_random_uuid()),
    portfolio_id           varchar(36) not null references portfolio (id) on delete restrict,
    fund_share_class_id    varchar(36) not null references fund_share_class (id) on delete restrict,
    position_date          date not null,
    quantity               DECIMAL(24,8) not null check (quantity >= 0),
    average_cost           DECIMAL(24,8) not null,
    cost_basis_currency_id varchar(36) not null references currency (id) on delete restrict,
    market_value           DECIMAL(24,6) not null,
    currency_id            varchar(36) not null references currency (id) on delete restrict,
    status                 VARCHAR(255) not null check (status in ('OPEN', 'CLOSED', 'RECONCILED')),
    computed_at            TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6),
    
    constraint uq_position_portfolio_fund_date unique (portfolio_id, fund_share_class_id, position_date)
);

create index ix_position_portfolio_date on position (portfolio_id, position_date desc);
