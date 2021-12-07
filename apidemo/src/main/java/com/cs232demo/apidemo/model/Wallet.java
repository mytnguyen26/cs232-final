package com.cs232demo.apidemo.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import javax.persistence.*;

public class Wallet {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="wallet_name")
    private String walletName;

    @Column(name="current_balanace", nullable=false)
    private double currBalance;
    

    public Wallet () {
    }

    public Wallet(@JsonProperty("walletID") UUID id, 
                  @JsonProperty("walletName") String walletName, 
                  @JsonProperty("currBalance") double currBalance) {
        this.id = id;
        this.walletName = walletName;
        this.currBalance = currBalance;
        
    }
 
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public double getCurrBalance() {
        return currBalance;
    }

    public void setCurrBalance(double currBalance) {
        this.currBalance = currBalance;
    }


    public boolean equals (Wallet otherWalletObj) {
        return  (this.currBalance == otherWalletObj.currBalance &&
                this.id == otherWalletObj.id &&
                this.walletName == otherWalletObj.walletName);
    }
}
