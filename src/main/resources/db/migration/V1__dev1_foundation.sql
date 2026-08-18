-- =====================================================================
-- Wealth Link Platform
-- Dev 1 — Foundation module
-- Identity & Access | Country / Market Config | Customer Management | Accounts
--
-- This is the dependency root: every other developer's module FKs into
-- CURRENCY / COUNTRY at minimum, and Trading/Ledger + Dividends/Reconciliation
-- FK into CUSTOMER / ACCOUNT. Per the architecture doc, this migration must
-- land first (Day 1) so the other three devs can wire real FKs on Day 2.
-- =====================================================================


-- ---------------------------------------------------------------------
-- Identity & Access
-- ---------------------------------------------------------------------

create table app_user (
    id            varchar(36) primary key default (gen_random_uuid()),
    username      VARCHAR(255) not null unique,
    email         VARCHAR(255) not null unique,
    password_hash VARCHAR(255) not null,
    status        VARCHAR(255) not null check (status in ('ACTIVE', 'DISABLED', 'LOCKED')),
    created_at    TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6),
    updated_at    TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6)
);

create table role (
    id   varchar(36) primary key default (gen_random_uuid()),
    name VARCHAR(255) not null unique
);

create table user_role (
    user_id varchar(36) not null references app_user (id) on delete cascade,
    role_id varchar(36) not null references role (id) on delete cascade,
    primary key (user_id, role_id)
);

-- ---------------------------------------------------------------------
-- Country / Market Configuration
-- ---------------------------------------------------------------------

create table currency (
    id                varchar(36) primary key default (gen_random_uuid()),
    iso_code            varchar(3) not null unique,
    name              VARCHAR(255) not null,
    minor_unit_digits smallint not null check (minor_unit_digits >= 0)
);

create table country (
    id                  varchar(36) primary key default (gen_random_uuid()),
    iso_code            varchar(2) not null unique,
    name                VARCHAR(255) not null,
    default_currency_id varchar(36) not null references currency (id) on delete restrict,
    timezone            VARCHAR(255) not null
);

create table market (
    id         varchar(36) primary key default (gen_random_uuid()),
    country_id varchar(36) not null references country (id) on delete restrict,
    name       VARCHAR(255) not null,
    mic_code   VARCHAR(255) not null,
    timezone   VARCHAR(255) not null,
    status     VARCHAR(255) not null check (status in ('ACTIVE', 'SUSPENDED', 'CLOSED'))
);

-- ---------------------------------------------------------------------
-- Customer Management
-- ---------------------------------------------------------------------

create table customer (
    id                        varchar(36) primary key default (gen_random_uuid()),
    customer_number           VARCHAR(255) not null unique,
    customer_type             VARCHAR(255) not null check (customer_type in ('INDIVIDUAL', 'CORPORATE')),
    status                    VARCHAR(255) not null check (status in ('ACTIVE', 'PENDING_KYC', 'SUSPENDED', 'CLOSED')),
    country_id                varchar(36) not null references country (id) on delete restrict,
    tax_residency_country_id  varchar(36) not null references country (id) on delete restrict,
    created_at                TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6),
    updated_at                TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6)
);

create table customer_contact (
    id          varchar(36) primary key default (gen_random_uuid()),
    customer_id varchar(36) not null references customer (id) on delete restrict,
    contact_type VARCHAR(255) not null check (contact_type in ('EMAIL', 'PHONE', 'ADDRESS')),
    value       VARCHAR(255) not null,
    is_primary  boolean not null default false
);

-- Only one primary contact per (customer, contact_type)
-- (MySQL does not support partial indexes with WHERE clauses)
-- create unique index uq_customer_contact_primary
--    on customer_contact (customer_id, contact_type)
--    where is_primary = true;

create table customer_identifier (
    id                 varchar(36) primary key default (gen_random_uuid()),
    customer_id        varchar(36) not null references customer (id) on delete restrict,
    id_type            VARCHAR(255) not null,
    id_value           VARCHAR(255) not null,
    issuing_country_id varchar(36) not null references country (id) on delete restrict
);

-- ---------------------------------------------------------------------
-- Accounts
-- ---------------------------------------------------------------------

create table account (
    id             varchar(36) primary key default (gen_random_uuid()),
    account_number VARCHAR(255) not null unique,
    account_type   VARCHAR(255) not null check (account_type in ('CASH', 'INVESTMENT')),
    currency_id    varchar(36) not null references currency (id) on delete restrict,
    country_id     varchar(36) not null references country (id) on delete restrict,
    status         VARCHAR(255) not null check (status in ('ACTIVE', 'DORMANT', 'CLOSED')),
    opened_at      TIMESTAMP(6) not null default CURRENT_TIMESTAMP(6)
);

create table account_owner (
    id             varchar(36) primary key default (gen_random_uuid()),
    account_id     varchar(36) not null references account (id) on delete restrict,
    customer_id    varchar(36) not null references customer (id) on delete restrict,
    ownership_role VARCHAR(255) not null check (ownership_role in ('PRIMARY', 'JOINT'))
);

-- ---------------------------------------------------------------------
-- Indexes (per Section 6 of the architecture doc, foundation subset)
-- ---------------------------------------------------------------------

create index ix_market_country on market (country_id);
create index ix_customer_country on customer (country_id);
create index ix_customer_contact_customer on customer_contact (customer_id);
create index ix_customer_identifier_customer on customer_identifier (customer_id);
create index ix_account_owner_account on account_owner (account_id);
create index ix_account_owner_customer on account_owner (customer_id);

-- customer_number / account_number / currency.iso_code / country.iso_code
-- already have unique indexes via their UNIQUE constraints above.

-- ---------------------------------------------------------------------
-- Seed reference data (NOK/SEK/DKK, NO/SE/DK) — per Day 4 handoff notes
-- ---------------------------------------------------------------------

insert into currency (iso_code, name, minor_unit_digits) values
    ('NOK', 'Norwegian Krone', 2),
    ('SEK', 'Swedish Krona', 2),
    ('DKK', 'Danish Krone', 2),
    ('EUR', 'Euro', 2);

insert into country (iso_code, name, default_currency_id, timezone)
select 'NO', 'Norway', id, 'Europe/Oslo' from currency where iso_code = 'NOK';

insert into country (iso_code, name, default_currency_id, timezone)
select 'SE', 'Sweden', id, 'Europe/Stockholm' from currency where iso_code = 'SEK';

insert into country (iso_code, name, default_currency_id, timezone)
select 'DK', 'Denmark', id, 'Europe/Copenhagen' from currency where iso_code = 'DKK';
