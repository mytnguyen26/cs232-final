CREATE TABLE Item
(
    id UUID PRIMARY KEY,
    item_name varchar(25) NOT NULL,
    item_desc varchar(225),
    item_type itemtype NOT NULL,
    item_quantity int,
    item_prioriy int,
    item_curr_price money NOT NULL,
    item_state itemstate NOT NULL,
    item_due_date date,
    add_date timestamp,
    update_date timestamp
)