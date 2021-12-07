package com.cs232demo.apidemo.dao;

import java.util.UUID;
import java.util.List;
import com.cs232demo.apidemo.model.*;

public interface IWalletDao {
    public List<Wallet> getCurrWallet();
    public boolean updateTransaction(UUID walletId, 
                                    UUID itemId, 
                                    String transType, 
                                    double amount);
}
