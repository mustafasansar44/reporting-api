-- Acquirers
INSERT INTO acquirers (id, name) VALUES (1, 'Acquirer A');
INSERT INTO acquirers (id, name) VALUES (2, 'Acquirer B');
INSERT INTO acquirers (id, name) VALUES (3, 'Acquirer C');
INSERT INTO acquirers (id, name) VALUES (4, 'Acquirer D');

-- $2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe = 1
-- Merchants   
INSERT INTO merchants (id, email, password) VALUES (1, 'mustafasansar44@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe');
INSERT INTO merchants (id, email, password) VALUES (2, 'mustafasansar45@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe');
INSERT INTO merchants (id, email, password) VALUES (3, 'mustafasansar46@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe');
INSERT INTO merchants (id, email, password) VALUES (4, 'mustafasansar47@gmail.com', '$2a$10$JEll4CYNvHxgkonkSDnJOeUwWfFW8P5UETRFZIygf.Pab8GgzkPMe');

-- Transactions
INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id) 
VALUES (1, '2025-10-05 10:00:00', 'USD', 100.50, 1, 1);

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id) 
VALUES (2, '2025-10-05 11:30:00', 'EUR', 250.75, 1, 1);

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id) 
VALUES (3, '2024-10-05 12:15:00', 'TRY', 500.00, 1, 1);

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id) 
VALUES (4, '2023-10-05 12:15:00', 'TRY', 500.00, 1, 1);

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id) 
VALUES (5, '2022-10-05 12:15:00', 'TRY', 500.00, 1, 2);

INSERT INTO transactions (id, created_at, currency, amount, merchant_id, acquirer_id) 
VALUES (6, '2022-10-05 12:15:00', 'TRY', 500.00, 2, 3);
