package com.cs232demo.apidemo.model;

import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class ItemNeed extends Item{
    @Column
    private int itemPriority;
    
    @Temporal(TemporalType.DATE)
    private Date dueDate; 

    public ItemNeed() {

    }

    public ItemNeed(@JsonProperty("name") String itemName, 
                    @JsonProperty("itemDesc") String itemDesc, int itemPriority) {
        super(itemName, itemDesc);
        this.itemPriority = itemPriority;
    }

    public int getItemPriority() {
        return itemPriority;
    }

    public void setItemPriority(int itemPriority) {
        this.itemPriority = itemPriority;
    }
    
}
