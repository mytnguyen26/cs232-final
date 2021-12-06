package com.cs232demo.apidemo.dao;
import java.util.List;
import java.util.UUID;

import com.cs232demo.apidemo.model.*;

public interface ItemDao {
    
    public void addItem(ItemNeed item);
    public void removeItem();
    public void changeItemQuantity();
    public ItemNeed findItemByID(UUID id);
    public List<ItemNeed> findAllItem();
    
}
