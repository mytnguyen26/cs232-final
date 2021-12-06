package com.cs232demo.apidemo.service;

import java.util.List;
import com.cs232demo.apidemo.dao.ItemDao;
import com.cs232demo.apidemo.model.ItemNeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemDao itemDao;

    @Autowired
    public ItemService(@Qualifier("sql") ItemDao itemDao) {
        this.itemDao = itemDao;
    }
   
    public void addItem(ItemNeed itemNeed) {
        itemDao.addItem(itemNeed);
    }


    public List<ItemNeed> getAllItem() {
        return itemDao.findAllItem();
    }
   
}
