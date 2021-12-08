
/**
 * This is a class to handle requets for data for Postgresql Database
 * When service call methods in this class, a connection to the database is
 * established and data is transfered either to the database, or return from
 * the database
 * This class implements the wallet dao and item dao interface 
 */
package com.cs232demo.apidemo.dao;

import java.util.List;
import java.util.UUID;
import java.sql.Date;
import java.sql.SQLException;

import com.cs232demo.apidemo.model.ItemNeed;
import com.cs232demo.apidemo.model.Wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository("sql")
public class SQLItemDataAccessService implements IItemDao<ItemNeed, UUID>, IWalletDao {
    private final JdbcTemplate jdbcTemplate; 

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
                            " from Item where item_state = 'add'";
        return jdbcTemplate.query(sql, new ItemRowMapper());
    }

    @Override
    public List<ItemNeed> findPaidItem() {
        
        final String sql = "Select id, "+
                            "item_name, " +
                            "item_desc, " +
                            "item_type, " +
                            "item_quantity, " +
                            "item_prioriy, " +
                            "item_curr_price, " +
                            "item_state, " +
                            "item_due_date " +
                            " from Item where item_state = 'pay'";
        return jdbcTemplate.query(sql, new ItemRowMapper());
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
    public boolean addItem(UUID id, ItemNeed entity) throws SQLException{ // throws Error adding to DB due to BadSQL here
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
                            entity.getDueDate(),
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
    public void changeItemStateToPay(final UUID entityID) {
        String sql = "UPDATE Item "+
                     "Set item_state = 'pay' "+
                     "Where id  = ?";

        jdbcTemplate.update(sql, entityID);
    }

    @Override
    public void removeItemByID(UUID entityID) {
        String item_state = "del";
        String sql = "UPDATE Item " +
                     "SET item_state = Cast(? as itemstate) " +
                     "WHERE id = ?";
        jdbcTemplate.update(sql, 
                            item_state, 
                            entityID);
        
    }

    @Override
    public List<Wallet> getCurrWallet() {
        String sql = "SELECT id, wallet_name, current_balanace from wallet";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String walletName = resultSet.getString("wallet_name");
            double currBalance = resultSet.getDouble("current_balanace");
            return new Wallet(id, walletName, currBalance);
        });
    }

    @Override
    public boolean updateTransaction(UUID walletId, 
                                    UUID itemId, 
                                    String transType, 
                                    double amount) {
        final String sql = "Call trans_item_test(?, ?, ?, ?)";
        jdbcTemplate.update(sql ,walletId, itemId, transType, amount);

        // SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("transaction_item");
        // SqlParameterSource in = new MapSqlParameterSource().addValue("walletid", walletId)
        //                                                    .addValue("itemid", itemId)
        //                                                    .addValue("transtype", transType)
        //                                                    .addValue("amount", amount);
        // jdbcCall.execute(in);
        return true;
    }
}
