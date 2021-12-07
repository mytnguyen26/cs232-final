Create or replace procedure transaction_item (
    walletid UUID,
    itemid UUID,
    transtype char(8),
    amount double precision
)

language plpgsql    
as $$
begin
    -- insert the new money transaction to Transaction
    Insert into Transactions 
    (
        wallet_id,
        item_id,
        transaction_type,
        transaction_amount,
        updated_ts 
    )
    values (
        walletid, 
        itemid,
        transtype,
        amount::NUMERIC::MONEY,
        CURRENT_TIMESTAMP
    );
    
    -- update wallet for latest current_balance
    update Wallet 
    set current_balanace = current_balanace - amount::NUMERIC::MONEY, 
        updated_ts = CURRENT_TIMESTAMP
    where id = walletid;

    commit;
end;$$