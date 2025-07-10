--liquibase formatted sql

--changeset isa:2025-07-08-insert-users
INSERT INTO users (email, password, role)
VALUES ('your_email1@gmail.com', 'password1', 'USER'),
       ('your_email2@gmail.com', 'password2', 'USER'),
       ('your_email3@gmail.com', 'password3', 'USER'),
       ('your_email4@gmail.com', 'password4', 'USER'),
       ('your_email5@gmail.com', 'password5', 'USER');

--changeset isa:2025-07-08-insert-user1-tasks
-- Задачи для user1 (ID = 1): 6 COMPLETED, 7 PENDING
INSERT INTO tasks (title, description, status, completed_at, user_id)
VALUES
-- Completed
('Task 1.1', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 1),
('Task 1.2', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 1),
('Task 1.3', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 1),
('Task 1.4', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 1),
('Task 1.5', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 1),
('Task 1.6', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 1),
-- Pending
('Task 1.7', 'Pending task', 'PENDING', NULL, 1),
('Task 1.8', 'Pending task', 'PENDING', NULL, 1),
('Task 1.9', 'Pending task', 'PENDING', NULL, 1),
('Task 1.10', 'Pending task', 'PENDING', NULL, 1),
('Task 1.11', 'Pending task', 'PENDING', NULL, 1),
('Task 1.12', 'Pending task', 'PENDING', NULL, 1),
('Task 1.13', 'Pending task', 'PENDING', NULL, 1);

--changeset isa:2025-07-08-insert-user2-tasks
-- Задачи для user2 (ID = 2): 3 COMPLETED
INSERT INTO tasks (title, description, status, completed_at, user_id)
VALUES ('Task 2.1', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 2),
       ('Task 2.2', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 2),
       ('Task 2.3', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 2);

--changeset isa:2025-07-08-insert-user3-tasks
-- Задачи для user3 (ID = 3): 4 PENDING
INSERT INTO tasks (title, description, status, completed_at, user_id)
VALUES ('Task 3.1', 'Pending task', 'PENDING', NULL, 3),
       ('Task 3.2', 'Pending task', 'PENDING', NULL, 3),
       ('Task 3.3', 'Pending task', 'PENDING', NULL, 3),
       ('Task 3.4', 'Pending task', 'PENDING', NULL, 3);

--changeset isa:2025-07-08-insert-user5-tasks
-- Задачи для user5 (ID = 5): 4 COMPLETED, 5 PENDING
INSERT INTO tasks (title, description, status, completed_at, user_id)
VALUES
-- Completed
('Task 5.1', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 5),
('Task 5.2', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 5),
('Task 5.3', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 5),
('Task 5.4', 'Completed task', 'COMPLETED', (now() - interval '1 day' * random())::timestamp, 5),
-- Pending
('Task 5.5', 'Pending task', 'PENDING', NULL, 5),
('Task 5.6', 'Pending task', 'PENDING', NULL, 5),
('Task 5.7', 'Pending task', 'PENDING', NULL, 5),
('Task 5.8', 'Pending task', 'PENDING', NULL, 5),
('Task 5.9', 'Pending task', 'PENDING', NULL, 5);
