CREATE TABLE Wallet (
    id UUID PRIMARY KEY,
    wallet_name varchar(225),
    current_balanace money NOT NULL,
    updated_ts timestamp 
)