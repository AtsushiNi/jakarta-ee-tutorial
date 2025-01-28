INSERT INTO users(first_name, last_name, email, hashed_password, created_at, updated_at) VALUES ('テスト', '太郎', 'test@gmail.com', 'PBKDF2WithHmacSHA512:3072:m2nu26XeSy40MneJ5m9W7BTtMY+pYMsHim7wgtIQa1RxIecovk0rPDPqR2PsZrBiGl4cA1NJVdvCA/dp8ccpIg==:HSks5rkAjxeZcPlPstha/xmI8DlxBhVxzNBdnGjuBbE=', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO reports(title, status, creator_id, created_at, updated_at) VALUES ('テスト明細1', 'C01_CREATING', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
