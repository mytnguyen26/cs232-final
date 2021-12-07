package com.cs232demo.apidemo.model;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private UUID id;

    @Column(name="item_name", nullable=false,length=25)
    private String itemName;

    @Column(name="item_desc", length=225)
    private String itemDesc;

    @Column(name="item_quantity")
    private int itemQuantity;

    @Column(name="item_priority", nullable = false)
    private int itemPriority;

    @Column(name="item_type", nullable = false)
    private String itemType;

    @Column(name="item_curr_price")
    private double currPrice;

    @Enumerated(EnumType.STRING)
    private ItemState itemState; 


    public Item() {
    }


    public Item(@JsonProperty("ItemId") UUID id, 
                @JsonProperty("name") String itemName, 
                @JsonProperty("itemDesc") String itemDesc, 
                @JsonProperty("itemQuantity") int itemQuantity,
                @JsonProperty("itemPriority") int itemPriority, 
                @JsonProperty("itemType") String itemType,
                @JsonProperty("itemPrice") double currPrice,
                @JsonProperty("itemState") ItemState itemState) {
        this.id = id;
        this.itemName  = itemName;
        this.itemDesc = itemDesc;
        this.itemType = itemType;
        this.itemQuantity = itemQuantity;
        this.itemPriority = itemPriority;
        this.currPrice = currPrice;
        this.itemState = itemState;
    }

    public UUID getid() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getItemDesc() {
        return itemDesc;
    }


    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }


    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getCurrPrice() {
        return currPrice;
    }

    public void setCurrPrice(double newPrice) {
        this.currPrice = newPrice;
    }

    public int getItemPriority() {
        return itemPriority;
    }


    public void setItemPriority(int itemPriority) {
        this.itemPriority = itemPriority;
    }


    public String getItemType() {
        return itemType;
    }


    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemState() {
        return itemState.toString();
    }

    public void setItemState(ItemState itemState) {
        this.itemState = itemState;
    }
}
