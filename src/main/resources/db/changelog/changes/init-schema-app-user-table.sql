-- liquibase formatted sql
-- changeset 'Ifeanyichukwu Otiwa':init_schema-app-user

CREATE TABLE IF NOT EXISTS app_user (
    id BINARY(16) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    created_on DATETIME NOT NULL
)