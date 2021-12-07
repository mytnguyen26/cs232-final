package com.cs232demo.apidemo.dao;

import java.util.List;
import java.util.UUID;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cs232demo.apidemo.model.ItemNeed;
import com.cs232demo.apidemo.model.ItemState;
import com.cs232demo.apidemo.dao.ItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("sql")
public class SQLItemDataAccessService implements IItemDao<ItemNeed, UUID>
                                         {
    private final JdbcTemplate jdbcTemplate; 
    private RowMapper<ItemNeed> itemRowMapper;

    @Autowired
    public SQLItemDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 
    @Override
    public List<ItemNeed> findAllItem() throws SQLException{
        final String sql = "Select id, "+
                            "item_name, " +
                            "item_desc, " +
                            "item_type, " +
                            "item_quantity, " +
                            "item_prioriy, " +
                            "item_curr_price, " +
                            "item_state, " +
                            "item_due_date " +
                            " from Item";
               
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID itemID = UUID.fromString(resultSet.getString("id"));
            String itemName = resultSet.getString("item_name");
            String itemDesc = resultSet.getString("item_desc");
            int itemQuantity = resultSet.getInt("item_quantity");
            int itemPriority = resultSet.getInt("item_prioriy");
            double currPrice = resultSet.getDouble("item_curr_price");
            ItemState itemState = ItemState.valueOf(resultSet.getString("item_state"));
            Date dueDate = resultSet.getDate("item_due_date");
            return new ItemNeed(itemID, 
                                itemName, 
                                itemDesc, 
                                itemQuantity,
                                itemPriority, 
                                currPrice,
                                itemState,
                                dueDate);
        }); 
    }

    @Override
    public List<ItemNeed> findItemByID(UUID id) {
        
        final String sql = "Select id, "+
                            "item_name, " +
                            "item_desc, " +
                            "item_type, " +
                            "item_quantity, " +
                            "item_prioriy, " +
                            "item_curr_price, " +
                            "item_state, " +
                            "item_due_date " +
                            " from Item where id = ?";
        return jdbcTemplate.query(sql, new ItemRowMapper(), id);
    }

    @Override
    public boolean addItem(UUID id, ItemNeed entity) { // throws Error adding to DB due to BadSQL here
        long millis=System.currentTimeMillis();  
        Date date=new java.sql.Date(millis);  
        final String sql = "INSERT INTO Item (" +
                            " id, " +
                            " item_name, " +
                            " item_desc, " +
                            " item_type, " +
                            " item_quantity, " +
                            " item_prioriy, "+
                            " item_curr_price, "+
                            " item_state, "+
                            " item_due_date, "+
                            " add_date, "+
                            " update_date) " +
                            "VALUES (?, ?, ?, cast(? as itemtype), ?, ?, ?::NUMERIC::MONEY, cast(? as itemstate), cast(? as date) , ? ,?)";

        jdbcTemplate.update(sql, 
                            id,
                            entity.getItemName(),
                            entity.getItemDesc(),
                            entity.getItemType(),
                            entity.getItemQuantity(),
                            entity.getItemPriority(),
                            entity.getCurrPrice(),
                            entity.getItemState(),
                            "9999/12/31",
                            date,
                            date
                            );
        return true;
    }

    @Override
    public void changeItemQuantity(UUID entityID) {
        // TODO Auto-generated method stub
        
        
    }

    @Override
    public void changeItemStateToPay(final ItemNeed entity) {
        // TODO
        

    }

    @Override
    public void removeItemByID(UUID entityID) {
        // TODO Auto-generated method stub
        String item_state = "del";
        String sql = "UPDATE Item " +
                     "SET item_state = Cast(? as itemstate) " +
                     "WHERE id = ?";
        jdbcTemplate.update(sql, 
                            item_state, 
                            entityID);
        
    }
}
