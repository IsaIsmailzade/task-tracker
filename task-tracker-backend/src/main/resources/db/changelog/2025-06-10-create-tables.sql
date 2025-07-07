--liquibase formatted sql

--changeset isa:2025-06-10-create-table-users
CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role     VARCHAR(64)         NOT NULL
);

--changeset isa:2025-06-10-create-table-tasks
CREATE TABLE IF NOT EXISTS tasks
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  VARCHAR(255),
    status       VARCHAR(64) NOT NULL,
    completed_at TIMESTAMP,
    user_id      BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE
);