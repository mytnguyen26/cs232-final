CREATE TABLE Transactions (
    transaction_id INT GENERATED ALWAYS AS IDENTITY,
    wallet_id UUID,
    item_id UUID,
    transaction_type varchar(8),
    transaction_amount money,
    updated_ts timestamp, 
    PRIMARY KEY(transaction_id),
        CONSTRAINT fk_wallet
            FOREIGN KEY(wallet_id) 
	            REFERENCES Wallet(id),
        CONSTRAINT fk_item
            FOREIGN KEY(item_id) 
	            REFERENCES Item(id)

)