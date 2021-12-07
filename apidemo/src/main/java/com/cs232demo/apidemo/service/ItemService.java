package com.cs232demo.apidemo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cs232demo.apidemo.dao.IItemDao;
import com.cs232demo.apidemo.dao.IWalletDao;
import com.cs232demo.apidemo.exception.NonExistingIDException;
import com.cs232demo.apidemo.model.ItemNeed;
import com.cs232demo.apidemo.model.Wallet;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final IItemDao<ItemNeed, UUID> itemDao;
    private final IWalletDao walletDao;

    @Autowired
    public ItemService(@Qualifier("sql") IItemDao<ItemNeed, UUID> itemDao, IWalletDao walletDao) {
        this.itemDao = itemDao;
        this.walletDao = walletDao;
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


    public List<ItemNeed> getPaidItem() {
        List<ItemNeed> itemNeed = itemDao.findPaidItem();
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
        List<ItemNeed> itemNeeds = getItem(itemId);
        try {
            itemNeeds = getItem(itemId);
            if (itemNeeds.size()==0) {
                throw new NonExistingIDException();
            }
            itemDao.removeItemByID(itemId);
        }
        catch (NonExistingIDException e) {
            System.out.println(itemId.toString() + " Does Not Exists");
        }
        
    }

    public List<ItemNeed> getItem(UUID itemId) {
        return itemDao.findItemByID(itemId);
    }

    public List<Wallet> getWallet() {
        return walletDao.getCurrWallet();
    }

    public void payForItem(UUID itemId) {
        String transType = "pay";
        UUID walletId = getWallet().get(0).getId();
        double amount = 0;
        System.out.println("-----------In Servivce");
        System.out.println(walletId);
        try {
            amount = getItem(itemId).get(0).getCurrPrice();
            if (amount == 0) {
                throw new NonExistingIDException();
            }
            // itemDao.changeItemStateToPay(itemId);
            System.out.println("---This is Item Price");
            System.out.println(amount);
            walletDao.updateTransaction(walletId, itemId, transType, amount);

        }
        catch (NonExistingIDException e) {
            System.out.println("Item not found!");
        }
        
    }

    private double generatePrice () {
        double min = 15.0;
        double max = 30.0; 
        return min + Math.random() * (max - min);
    }
   
}
