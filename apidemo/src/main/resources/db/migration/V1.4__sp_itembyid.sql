CREATE OR REPLACE PROCEDURE SelectByID (
    id UUID
)

language plpgsql    
as $$
begin

    Select id, 
        item_name,  
        item_desc,  
        item_type,  
        item_quantity,  
        item_prioriy,  
        item_curr_price,  
        item_state,  
        item_due_date  
    from Item 
    where id = id;

    commit;
end;$$
