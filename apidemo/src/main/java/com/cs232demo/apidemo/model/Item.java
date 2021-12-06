package com.cs232demo.apidemo.model;
import java.util.UUID;
import javax.persistence.*;

@Entity
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private UUID id;

    @Column
    private String itemName;

    @Column
    private String itemDesc;

    @Column
    private int itemQuantity;


    @Column
    private double currPrice;

    @Enumerated(EnumType.STRING)
    private ItemState itemState; 


    public Item() {
    }


    public Item(String itemName, String itemDesc) {
        this.itemName  = itemName;
        this.itemDesc = itemDesc;
        this.itemQuantity = 1;
        this.currPrice = generatePrice();
        this.itemState = ItemState.add;
    }

    public UUID getid() {
        return id;
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


    public void setCurrPrice() {
        this.currPrice = generatePrice ();
    }


    public ItemState getItemState() {
        return itemState;
    }


    public void setItemState(ItemState itemState) {
        this.itemState = itemState;
    }

    private double generatePrice () {
        double min = 15.0;
        double max = 30.0; 
        return min + Math.random() * (max - min);
    }
}
