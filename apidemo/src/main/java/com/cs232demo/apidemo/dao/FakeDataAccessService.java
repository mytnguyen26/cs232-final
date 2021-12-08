package com.cs232demo.apidemo.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository("fake")
public class FakeDataAccessService implements IItemDao {

    @Override
    public List findItemByID(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List findAllItem() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List findPaidItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addItem(UUID id, Object entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void changeItemQuantity(Object entityID) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeItemStateToPay(UUID entityID) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeItemByID(Object entityID) {
        // TODO Auto-generated method stub
        
    }
    
}
