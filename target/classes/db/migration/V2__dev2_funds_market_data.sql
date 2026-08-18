-- =====================================================================
-- Wealth Link Platform
-- Dev 2 — Funds & Market Data module
-- Funds | Share Classes | Providers | FX Rates | Fund Prices | Imports
--
-- Depends on V1__dev1_foundation.sql for CURRENCY / COUNTRY. Real FKs are
-- used directly (no UUID stubs) because Dev 1's foundation module is
-- already merged into this codebase.
--
-- Everything Dev 3 (Portfolio/Trading/Ledger) needs from this module is
-- FUND_SHARE_CLASS and PROVIDER — see README.local.md handoff notes.
-- =====================================================================

-- ---------------------------------------------------------------------
-- Funds
-- ---------------------------------------------------------------------

create table fund (
    id                  UUID primary key default (gen_random_uuid()),
    isin                varchar(12) not null unique,
    name                VARCHAR(255) not null,
    base_currency_id    UUID not null references currency (id) on delete restrict,
    domicile_country_id UUID not null references country (id) on delete restrict,
    status              VARCHAR(255) not null check (status in ('ACTIVE', 'SUSPENDED', 'CLOSED')),
    inception_date      date,
    created_at          TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6),
    updated_at          TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6)
);

create table fund_share_class (
    id          UUID primary key default (gen_random_uuid()),
    fund_id     UUID not null references fund (id) on delete restrict,
    class_code  varchar(30) not null,
    name        VARCHAR(255) not null,
    currency_id UUID not null references currency (id) on delete restrict,
    status      VARCHAR(255) not null check (status in ('ACTIVE', 'CLOSED')),
    constraint uq_fund_share_class_fund_code unique (fund_id, class_code)
);

-- ---------------------------------------------------------------------
-- Providers
-- ---------------------------------------------------------------------

create table provider (
    id     UUID primary key default (gen_random_uuid()),
    code   varchar(30) not null unique,
    name   VARCHAR(255) not null,
    status VARCHAR(255) not null check (status in ('ACTIVE', 'DISABLED'))
);

create table fund_provider_mapping (
    id                  UUID primary key default (gen_random_uuid()),
    fund_share_class_id UUID not null references fund_share_class (id) on delete restrict,
    provider_id         UUID not null references provider (id) on delete restrict,
    external_fund_id    VARCHAR(255) not null,
    constraint uq_fund_provider_mapping_provider_external_id unique (provider_id, external_fund_id)
);

-- ---------------------------------------------------------------------
-- FX Rates
-- ---------------------------------------------------------------------

create table fx_rate_source (
    id   UUID primary key default (gen_random_uuid()),
    code varchar(30) not null unique,
    name VARCHAR(255) not null
);

create table fx_rate (
    id                 UUID primary key default (gen_random_uuid()),
    base_currency_id   UUID not null references currency (id) on delete restrict,
    quote_currency_id  UUID not null references currency (id) on delete restrict,
    rate_date          date not null,
    rate_type          VARCHAR(255) not null check (rate_type in ('SPOT', 'CLOSE')),
    source_id          UUID not null references fx_rate_source (id) on delete restrict,
    rate               DECIMAL(24, 8) not null check (rate > 0),
    constraint uq_fx_rate_business_key
        unique (base_currency_id, quote_currency_id, rate_date, rate_type, source_id)
);

-- ---------------------------------------------------------------------
-- Imports (job -> batch -> item), created before fund_price because
-- fund_price.import_batch_id references import_batch.
-- ---------------------------------------------------------------------

create table import_job (
    id          UUID primary key default (gen_random_uuid()),
    name        VARCHAR(255) not null,
    provider_id UUID not null references provider (id) on delete restrict,
    job_type    VARCHAR(255) not null check (job_type in ('FUND_PRICE_IMPORT', 'FX_RATE_IMPORT')),
    status      VARCHAR(255) not null check (status in ('ACTIVE', 'DISABLED')),
    created_at  TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6)
);

create table import_batch (
    id              UUID primary key default (gen_random_uuid()),
    import_job_id   UUID not null references import_job (id) on delete restrict,
    idempotency_key VARCHAR(255) not null unique,
    status          VARCHAR(255) not null check (status in ('PENDING', 'RUNNING', 'COMPLETED', 'FAILED')),
    started_at      TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6),
    completed_at    TIMESTAMP(6),
    total_items     integer not null default 0,
    success_count   integer not null default 0,
    failure_count   integer not null default 0,
    version         integer not null default 0
);

-- ---------------------------------------------------------------------
-- Fund prices
-- ---------------------------------------------------------------------

create table fund_price (
    id                   UUID primary key default (gen_random_uuid()),
    fund_share_class_id  UUID not null references fund_share_class (id) on delete restrict,
    price_date           date not null,
    price_type           VARCHAR(255) not null check (price_type in ('NAV', 'BID', 'ASK')),
    provider_id          UUID not null references provider (id) on delete restrict,
    currency_id          UUID not null references currency (id) on delete restrict,
    price                DECIMAL(24, 8) not null check (price > 0),
    import_batch_id      UUID references import_batch (id) on delete set null,
    -- Critical business rule (DEV2-D1 acceptance criteria): uniqueness is
    -- anchored to the internal fund_share_class_id, not any provider's
    -- external identifier, per the architecture doc.
    constraint uq_fund_price_business_key
        unique (fund_share_class_id, price_date, price_type, provider_id)
);

-- import_item comes after fund_price because it optionally points at the
-- fund_price row it produced.
create table import_item (
    id               UUID primary key default (gen_random_uuid()),
    import_batch_id  UUID not null references import_batch (id) on delete restrict,
    raw_payload      jsonb not null,
    status           VARCHAR(255) not null check (status in ('PENDING', 'SUCCESS', 'FAILED')),
    error_details    VARCHAR(255),
    fund_price_id    UUID references fund_price (id) on delete set null,
    processed_at     TIMESTAMP(6)
);

-- ---------------------------------------------------------------------
-- Indexes (per Section 31 of the architecture doc, Dev 2 subset)
-- ---------------------------------------------------------------------

create index ix_fund_base_currency on fund (base_currency_id);
create index ix_fund_domicile_country on fund (domicile_country_id);
create index ix_fund_share_class_fund on fund_share_class (fund_id);
create index ix_fund_share_class_currency on fund_share_class (currency_id);
create index ix_fund_provider_mapping_share_class on fund_provider_mapping (fund_share_class_id);
create index ix_fund_provider_mapping_provider on fund_provider_mapping (provider_id);

create index ix_fx_rate_base_quote_date on fx_rate (base_currency_id, quote_currency_id, rate_date desc);

create index ix_fund_price_share_class_date on fund_price (fund_share_class_id, price_date desc);
create index ix_fund_price_import_batch on fund_price (import_batch_id);

create index ix_import_batch_job on import_batch (import_job_id);
create index ix_import_item_batch on import_item (import_batch_id);
create index ix_import_item_fund_price on import_item (fund_price_id);

-- code/isin/class_code/idempotency_key already have unique indexes via
-- their UNIQUE constraints above.

-- ---------------------------------------------------------------------
-- Seed reference data — per Day 4 handoff notes (DEV2-D4)
-- ---------------------------------------------------------------------

insert into provider (code, name, status) values
    ('MORNINGSTAR', 'Morningstar', 'ACTIVE'),
    ('BLOOMBERG', 'Bloomberg', 'ACTIVE'),
    ('MANUAL', 'Manual Entry', 'ACTIVE');

insert into fx_rate_source (code, name) values
    ('ECB', 'European Central Bank'),
    ('MANUAL', 'Manual Entry');
