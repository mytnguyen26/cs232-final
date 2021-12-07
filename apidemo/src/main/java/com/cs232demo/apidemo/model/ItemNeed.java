package com.cs232demo.apidemo.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class ItemNeed extends Item{
    
    @Column(name="item_due_date")
    private String dueDate; 
    
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
                    @JsonProperty("itemDue") @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd") String dueDate) {
        
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


    public LocalDate getDueDate() {
        LocalDate date = LocalDate.parse(dueDate);
        return date;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
}
