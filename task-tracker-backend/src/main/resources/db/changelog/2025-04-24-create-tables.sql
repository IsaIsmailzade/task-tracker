--liquibase formatted sql

--changeset isa:2025-04-24-create-table-user
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL
);

--changeset isa:2025-04-24-create-table-tasks
CREATE TABLE tasks
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    status       VARCHAR(64),
    completed_at TIMESTAMP,
    user_id      INTEGER      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
);