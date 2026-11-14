-- Flyway Migration V7: Ensure critical sequences exist in production PostgreSQL
CREATE SEQUENCE IF NOT EXISTS trx_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS passbook_no_seq START WITH 1 INCREMENT BY 1;
