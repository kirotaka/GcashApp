-- Schema for H2 test database
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    number BIGINT,
    pin VARCHAR(4),
    password VARCHAR(255),
    balance DOUBLE NOT NULL DEFAULT 0.0
);

CREATE TABLE IF NOT EXISTS balance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL DEFAULT 0.0,
    user_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT,
    receiver_id BIGINT,
    amount DOUBLE NOT NULL,
    name VARCHAR(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    account_id BIGINT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transfer_to_id BIGINT,
    transfer_from_id BIGINT
);

CREATE TABLE IF NOT EXISTS cash_transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    name VARCHAR(255),
    account_id BIGINT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transfer_to_id BIGINT,
    transfer_from_id BIGINT
);
