package com.cs232demo.apidemo.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class ItemNeed extends Item{
    
    @Column(name="item_due_date")
    private Date dueDate; 
    
    public ItemNeed() {

    }

    public ItemNeed(UUID id, 
                    String itemName, 
                    String itemDesc, 
                    int itemQuantity,
                    int itemPriority, 
                    String itemType,
                    double currPrice,
                    ItemState itemState,
                    @JsonProperty("itemDue") Date dueDate) {
        
        super(id, 
              itemName, 
              itemDesc, 
              itemQuantity,
              itemPriority, 
              itemType,
              currPrice, 
              itemState
              );
        this.dueDate = dueDate;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date date) {
        this.dueDate = date;
    }
    
}
