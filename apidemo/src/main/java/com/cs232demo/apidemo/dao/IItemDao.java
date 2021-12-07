package com.cs232demo.apidemo.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.cs232demo.apidemo.model.ItemNeed;

public interface IItemDao<T, K> {

    public List<ItemNeed> findItemByID(UUID id);
    public List<T> findAllItem() throws SQLException;
    public List<T> findPaidItem();
    public boolean addItem(UUID id, final T entity);     // add new item 
    public void changeItemQuantity(final K entityID);    // update item quantity by id
    public void changeItemStateToPay(final UUID entityID);
    public void removeItemByID(final K entityID);            // remove item by id
    
}
