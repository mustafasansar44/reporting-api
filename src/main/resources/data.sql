-- ================================================================
-- REPORTING API - COMPREHENSIVE TEST DATA
-- Password Hash: $2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe (represents: 1)
-- ================================================================

-- ================================================================
-- ACQUIRERS (Payment Gateway Providers)
-- ================================================================
INSERT INTO acquirers (id, name, code) VALUES (1, 'PaySystem1', 'PS1');
INSERT INTO acquirers (id, name, code) VALUES (2, 'PaySystem2', 'PS2');
INSERT INTO acquirers (id, name, code) VALUES (3, 'PaySystem3', 'PS3');
INSERT INTO acquirers (id, name, code) VALUES (4, 'PaySystem4', 'PS4');
INSERT INTO acquirers (id, name, code) VALUES (5, 'PaySystem5', 'PS5');
INSERT INTO acquirers (id, name, code) VALUES (6, 'PaySystem6', 'PS6');

-- ================================================================
-- MERCHANTS (Businesses accepting payments)
-- ================================================================
INSERT INTO merchants (id, email, password, name, reference_no) VALUES (1, 'mustafasansar44@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe', 'Merchant1', 'REF_NO_1');
INSERT INTO merchants (id, email, password, name, reference_no) VALUES (2, 'mustafasansar45@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe', 'Merchant2', 'REF_NO_2');
INSERT INTO merchants (id, email, password, name, reference_no) VALUES (3, 'mustafasansar46@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe', 'Merchant3', 'REF_NO_3');
INSERT INTO merchants (id, email, password, name, reference_no) VALUES (4, 'mustafasansar47@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe', 'Merchant4', 'REF_NO_4');
INSERT INTO merchants (id, email, password, name, reference_no) VALUES (5, 'mustafasansar48@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe', 'Merchant5', 'REF_NO_5');
INSERT INTO merchants (id, email, password, name, reference_no) VALUES (6, 'mustafasansar6@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe', 'Merchant6', 'REF_NO_6');
INSERT INTO merchants (id, email, password, name, reference_no) VALUES (7, 'mustafasansar7@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe', 'Merchant7', 'REF_NO_7');
INSERT INTO merchants (id, email, password, name, reference_no) VALUES (8, 'mustafasansar8@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe', 'Merchant8', 'REF_NO_8');

-- ================================================================
-- CUSTOMERS (End users making purchases)
-- ================================================================
INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (1, '4242424242424242', 'john.doe@example.com', 'John', 'Doe');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (2, '5555555555554444', 'jane.smith@example.com', 'Jane', 'Smith');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (3, '378282246310005', 'ahmet.yilmaz@example.com', 'Ahmet', 'Yılmaz');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (4, '6011111111111117', 'emily.wilson@example.com', 'Emily', 'Wilson');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (5, '3530111333300000', 'mehmet.kaya@example.com', 'Mehmet', 'Kaya');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (6, '5105105105105100', 'sarah.connor@example.com', 'Sarah', 'Connor');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (7, '4111111111111111', 'zeynep.demir@example.com', 'Zeynep', 'Demir');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (8, '4012888888881881', 'david.brown@example.com', 'David', 'Brown');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (9, '371449635398431', 'ayse.celik@example.com', 'Ayşe', 'Çelik');

INSERT INTO customers (id, card_number, email, billing_first_name, billing_last_name) 
VALUES (10, '6011000990139424', 'michael.johnson@example.com', 'Michael', 'Johnson');

-- ================================================================
-- TRANSACTIONS (Payment transactions)
-- ================================================================
-- Operations
-- ================================================================
-- DIRECT, REFUND, _3D, _3DAUTH, STORED

-- 2025 Transactions (Current Year)
INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (1, '2025-10-05 10:00:00', 'USD', 100.50, 1, 1, 1, 'DIRECT', 'CREDITCARD', true, true, 'DO_NOT_HONOR');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (2, '2025-10-05 11:30:00', 'EUR', 250.75, 1, 1, 2, '_3DAUTH', 'CREDITCARD', true, true, 'DO_NOT_HONOR');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (3, '2025-10-04 14:22:00', 'TRY', 1500.00, 2, 3, 3, 'REFUND', 'CREDITCARD', true, true, 'DO_NOT_HONOR');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (4, '2025-10-03 09:15:00', 'USD', 450.00, 3, 2, 4, '_3D', 'CUP', true, true, 'DO_NOT_HONOR');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (5, '2025-10-02 16:45:00', 'GBP', 320.99, 4, 4, 5, 'STORED', 'CUP', true, true, 'DO_NOT_HONOR');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (6, '2025-10-01 08:30:00', 'EUR', 125.50, 5, 5, 6, 'REFUND', 'CUP', true, true, 'INVALID_TRANSACTION');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (7, '2025-09-30 12:00:00', 'USD', 789.99, 6, 6, 7, 'STORED', 'IDEAL', true, true, 'INVALID_TRANSACTION');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (8, '2025-09-29 17:20:00', 'TRY', 2340.50, 7, 3, 8, 'STORED', 'IDEAL', true, true, 'INVALID_TRANSACTION');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (9, '2025-09-28 10:10:00', 'EUR', 567.25, 8, 1, 9, '_3DAUTH', 'GIROPAY', true, true, 'INVALID_TRANSACTION');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (10, '2025-09-27 13:45:00', 'USD', 1200.00, 1, 2, 10, 'DIRECT', 'GIROPAY', true, true, 'INVALID_TRANSACTION');

-- 2024 Transactions
INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (11, '2024-12-15 11:00:00', 'USD', 890.00, 2, 1, 1, '_3DAUTH', 'MISTERCASH', true, true, 'INVALID_CARD');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (12, '2024-11-20 14:30:00', 'EUR', 450.75, 3, 2, 2, 'DIRECT', 'MISTERCASH', true, true, 'INVALID_CARD');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (13, '2024-10-10 09:15:00', 'TRY', 3500.00, 4, 3, 3, '_3DAUTH', 'MISTERCASH', true, true, 'INVALID_CARD');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (14, '2024-09-05 16:20:00', 'GBP', 678.90, 5, 4, 4, 'DIRECT', 'STORED', true, true, 'INVALID_CARD');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (15, '2024-08-22 10:45:00', 'USD', 234.50, 6, 5, 5, 'DIRECT', 'PAYTOCARD', true, true, 'INVALID_CARD');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (16, '2024-07-18 13:00:00', 'EUR', 890.25, 7, 6, 6, 'DIRECT', 'PAYTOCARD', true, true, 'INVALID_CARD');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (17, '2024-06-25 15:30:00', 'TRY', 1890.00, 8, 3, 7, '_3DAUTH', 'PAYTOCARD', true, true, 'NOT_SUFFICIENT_FUNDS');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (18, '2024-05-12 11:20:00', 'USD', 567.80, 1, 1, 8, '_3DAUTH', 'PAYTOCARD', true, true, 'NOT_SUFFICIENT_FUNDS');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (19, '2024-04-08 14:15:00', 'GBP', 1234.99, 2, 2, 9, 'DIRECT', 'CEPBANK', true, true, 'NOT_SUFFICIENT_FUNDS');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (20, '2024-03-14 09:00:00', 'EUR', 345.60, 3, 4, 10, 'DIRECT', 'CITADEL', true, true, 'NOT_SUFFICIENT_FUNDS');

-- 2023 Transactions
INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
    VALUES (21, '2023-12-20 10:30:00', 'TRY', 2500.00, 1, 3, 1, 'DIRECT', 'CREDITCARD', false, true, 'INCORRECT_PIN');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (22, '2023-11-15 12:00:00', 'USD', 678.50, 2, 1, 2, 'STORED', 'CREDITCARD', false, true, 'INCORRECT_PIN');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (23, '2023-10-10 14:45:00', 'EUR', 456.75, 3, 2, 3, 'STORED', 'CREDITCARD', false, true, 'INCORRECT_PIN');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (24, '2023-09-05 16:30:00', 'GBP', 789.99, 4, 5, 4, 'DIRECT', 'CREDITCARD', false, true, 'INVALID_COUNTRY_ASSOCIATION');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (25, '2023-08-22 11:15:00', 'USD', 1500.00, 5, 6, 5, 'DIRECT', 'CREDITCARD', false, true, 'INVALID_COUNTRY_ASSOCIATION');

-- 2022 Transactions
INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (26, '2022-12-28 09:00:00', 'TRY', 1800.00, 6, 3, 6, 'DIRECT', 'CREDITCARD', false, true, 'INVALID_COUNTRY_ASSOCIATION');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (27, '2022-11-18 13:30:00', 'EUR', 990.50, 7, 1, 7, 'DIRECT', 'CREDITCARD', false, true, 'INVALID_COUNTRY_ASSOCIATION');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (28, '2022-10-05 15:45:00', 'USD', 567.25, 8, 2, 8, 'DIRECT', 'CREDITCARD', false, true, 'INVALID_COUNTRY_ASSOCIATION');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (29, '2022-09-12 10:20:00', 'GBP', 1234.00, 1, 4, 9, 'DIRECT', 'CREDITCARD', false, true, 'CURRENCY_NOT_ALLOWED');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
    VALUES (30, '2022-08-25 14:00:00', 'TRY', 3200.50, 2, 3, 10, 'DIRECT', 'CREDITCARD', false, false, 'CURRENCY_NOT_ALLOWED');

-- Additional High-Value Transactions
INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (31, '2025-10-05 18:00:00', 'USD', 5000.00, 1, 1, 1, 'DIRECT', 'CREDITCARD', false, false, 'CURRENCY_NOT_ALLOWED');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (32, '2025-10-04 19:30:00', 'EUR', 4500.99, 2, 2, 2, 'DIRECT', 'CREDITCARD', false, false, 'CURRENCY_NOT_ALLOWED');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (33, '2025-10-03 20:15:00', 'TRY', 15000.00, 3, 3, 3, 'DIRECT', 'CREDITCARD', false, false, 'CURRENCY_NOT_ALLOWED');

-- Transactions with NULL customer_id (for testing nullable relationship)
INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (34, '2025-10-02 08:00:00', 'USD', 99.99, 4, 1, 4, 'DIRECT', 'CREDITCARD', false, false, '_3D_SECURE_TRANSPORT_ERROR');

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id, customer_id, operation, payment_method, received, refundable, error_code) 
VALUES (35, '2025-10-01 09:30:00', 'EUR', 199.99, 5, 2, 5, 'DIRECT', 'CREDITCARD', false, false, 'TRANSACTION_NOT_PERMITTED_TO_CARDHOLDER');

-- ================================================================
-- END OF DATA
-- Total Records: 6 Acquirers, 8 Merchants, 10 Customers, 35 Transactions
-- ================================================================
