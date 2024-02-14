-- liquibase formatted sql
-- changeset 'ifeanyichukwu Otiwa':init-paysack-payments-table

CREATE TABLE IF NOT EXISTS paystack_payment
(
    id                BINARY(16) PRIMARY KEY,
    user_id           BINARY(16)   NOT NULL,
    reference         VARCHAR(255)   NOT NULL,
    amount            DECIMAL(20, 4) NOT NULL,
    gateway_response  VARCHAR(255)   NOT NULL,
    paid_at           VARCHAR(255)   NOT NULL,
    created_at        VARCHAR(255)   NOT NULL,
    channel           VARCHAR(255)   NOT NULL,
    currency          VARCHAR(255)   NOT NULL,
    ip_address        VARCHAR(255)   NOT NULL,
    pricing_plan_type ENUM ('BASIC', 'PREMIUM', 'STANDARD') DEFAULT NULL,
    created_on         DATETIME       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE CASCADE ON UPDATE CASCADE
)