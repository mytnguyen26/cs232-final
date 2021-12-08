/**
 * This class handles and direct requests from the Controller to the
 * appropriate business logic and databases
 */
package com.cs232demo.apidemo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cs232demo.apidemo.dao.IItemDao;
import com.cs232demo.apidemo.dao.IWalletDao;
import com.cs232demo.apidemo.exception.*;
import com.cs232demo.apidemo.model.ItemNeed;
import com.cs232demo.apidemo.model.Wallet;

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
   
    /**
     * This method service the getAllItem request from the Controller
     * it retrieve from the Database all items that are active (marked as 'add')
     * @return
     */
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


    /**
     * This method service the getPaidItem request from the Controller
     * it retrieve from the Database all items that are marked as 'pay'
     * @return
     */
    public List<ItemNeed> getPaidItem() {
        List<ItemNeed> itemNeed = itemDao.findPaidItem();
        return itemNeed;
    }

    /**
     * This method service the addItem request from the Controller
     * it insert the new item to the database
     * @param itemNeed
     */
    public void addItem(ItemNeed itemNeed) {
        // generate UUID
        UUID id = UUID.randomUUID();
        itemNeed.setCurrPrice(generatePrice());
        itemNeed.setItemName(checkString(itemNeed.getItemName()));  // remove special chars
        
        try {
            checkNegativeNumber(itemNeed.getItemQuantity());
        }
        catch (NegativeNumberException e1) {
            System.out.println("Cannot have negative quantity");
            System.out.println("Replace with default number 1");
            itemNeed.setItemQuantity(1);
        }

        try {
            checkNegativeNumber(itemNeed.getItemPriority());
        }
        catch (NegativeNumberException e2) {
            System.out.println("Cannot have negative priority");
            System.out.println("Replace with default number 1");
            itemNeed.setItemPriority(1);
        }

        System.out.println("Adding Item to SQL");
        System.out.println(itemNeed.getItemName());
        try {
            itemDao.addItem(id, itemNeed);
        }
        catch (SQLException e3) {
            System.out.println("SQL Exception Raised, Check the SQL");
        }
        
    }

    /**
     * This method service the removeItem request from the Controller
     * it checks if the item id in the database, and change its
     * state to delete
     */
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

    /**
     * this method service the getWallet request from the Controller
     * it retrieve from the Database information about the Balance and Name
     * @return
     */
    public List<Wallet> getWallet() {
        return walletDao.getCurrWallet();
    }

    /**
     * This method service the request to payforitem from the Controller
     * it first ask the Database for the price of the item, and the wallet
     * then, it checks if we have enough current balance from wallet to perform
     * the service. If yes, then it asks the database to update the balance, item
     * states, and add a pay transaction type.
     * @param itemId
     */
    public void payForItem(UUID itemId) {
        String transType = "pay";
        Wallet wallet = getWallet().get(0);   // get wallet, currently there's only 1 wallet
        ItemNeed itemNeed;
        double amount = 0;

        System.out.println("-----------In Servivce");
        System.out.println(wallet.getId());

        try {
            itemNeed = getItem(itemId).get(0);
            if (itemNeed.getItemName().isBlank()) {
                throw new NonExistingIDException();
            }
            amount = getItem(itemId).get(0).getCurrPrice();
            if (wallet.getCurrBalance() < amount) {
                throw new NegativeBalanceException();
            }

            System.out.println("---This is Item Price");
            System.out.println(amount);
            walletDao.updateTransaction(wallet.getId(), itemId, transType, amount);
        }
        catch (NonExistingIDException e) {
            System.out.println("Item not found!");
        }
        catch (NegativeBalanceException e2) {
            System.out.printf("Not enough balance for the item %s\n", itemId.toString());
        }
        
    }

    /**
     * This method generate some price for the item
     * for demo purpose
     */
    private double generatePrice () {
        double min = 15.0;
        double max = 30.0; 
        return min + Math.random() * (max - min);
    }

    /**
     * This helper method remove special characters from input string
     * @param inputString
     * @return
     */
    private String checkString(String inputString) {
        String newString = inputString.replaceAll("[^a-zA-Z]+", "");
        return newString;
    }

    /**
     * This method check for numeric input coming from user input for either
     * negative number
     * @param inputValue
     * @throws NegativeNumberException
     */
    private void checkNegativeNumber(int inputValue) throws NegativeNumberException {
        if (inputValue < 0) {
            throw new NegativeNumberException();
        }
    }
}
