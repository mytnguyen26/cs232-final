package com.cs232demo.apidemo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cs232demo.apidemo.dao.IItemDao;
import com.cs232demo.apidemo.exception.NonExistingIDException;
import com.cs232demo.apidemo.model.ItemNeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final IItemDao<ItemNeed, UUID> itemDao;

    @Autowired
    public ItemService(@Qualifier("sql") IItemDao<ItemNeed, UUID> itemDao) {
        this.itemDao = itemDao;
    }
   
    
    public List<ItemNeed> getAllItem() {
        List<ItemNeed> itemNeed = new ArrayList<ItemNeed>();
        try {
            itemNeed = itemDao.findAllItem();
        }
        catch (SQLException e) {
            System.out.println("Run Time Error When Trying to Parse SQL");
        }
        return itemNeed;
    }

    public void addItem(ItemNeed itemNeed) {
        // generate UUID
        UUID id = UUID.randomUUID();
        itemNeed.setCurrPrice(generatePrice());
        System.out.println(itemNeed.getItemName());
        itemDao.addItem(id, itemNeed);
    }

    public void removeItem(UUID itemId) {
        try {
            List<ItemNeed> itemNeeds = getItem(itemId);
            if (itemNeeds.size()==0) {
                throw new NonExistingIDException();
            }
            itemDao.removeItemByID(itemId);
        }
        catch (Exception e) {
            System.out.println(itemId.toString() + " Does Not Exists");
        }
        
    }

    public List<ItemNeed> getItem(UUID itemId) {
        return itemDao.findItemByID(itemId);
    }

    private double generatePrice () {
        double min = 15.0;
        double max = 30.0; 
        return min + Math.random() * (max - min);
    }
   
}
