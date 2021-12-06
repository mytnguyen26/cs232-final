package com.cs232demo.apidemo.dao;

import java.util.List;
import java.util.UUID;

import com.cs232demo.apidemo.model.ItemNeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("sql")
public class SQLItemDataAccessService implements ItemDao {
    // private List<ItemNeed> DB = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SQLItemDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addItem(ItemNeed item) {
        // TODO
    }

    @Override
    public void removeItem() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeItemQuantity() {
        // TODO Auto-generated method stub
        
    }

    // 
    @Override
    public List<ItemNeed> findAllItem() {
        final String sql = "Select item_name," +
                            "item_desc," +
                            "item_type," +
                            "item_quantity," +
                            "item_prioriy," +
                            "item_curr_price" +
                            " from Item";

        return jdbcTemplate.query(sql, (resultSet, i) -> {
            String itemName = resultSet.getString("item_name");
            String itemDesc = resultSet.getString("item_desc");
            return new ItemNeed(itemName, itemDesc, 1
            );
        }); 
    }

    @Override
    public ItemNeed findItemByID(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
