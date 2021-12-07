package com.cs232demo.apidemo.dao;
import com.cs232demo.apidemo.model.ItemNeed;
import com.cs232demo.apidemo.model.ItemState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;

public class ItemRowMapper implements RowMapper<ItemNeed> {
    @Override
    public ItemNeed mapRow(ResultSet rs, int rowNum) throws SQLException {
        ItemNeed itemNeed = new ItemNeed();
        itemNeed.setId(UUID.fromString(rs.getString("id")));
        itemNeed.setItemName(rs.getString("item_name"));
        itemNeed.setItemDesc(rs.getString("item_desc"));
        itemNeed.setItemType(rs.getString("item_type"));
        itemNeed.setItemQuantity(rs.getInt("item_quantity"));
        itemNeed.setItemPriority(rs.getInt("item_prioriy"));
        itemNeed.setCurrPrice(rs.getDouble("item_curr_price"));
        itemNeed.setItemState(ItemState.valueOf(rs.getString("item_state")));
        itemNeed.setDueDate(rs.getDate("item_due_date").toString());

        return itemNeed;

    }
}
