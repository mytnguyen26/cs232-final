package com.cs232demo.apidemo.api;

import com.cs232demo.apidemo.service.ItemService;
import com.cs232demo.apidemo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RequestMapping("api/v1/Item")
@RestController
@CrossOrigin("*")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public void addItem(@RequestBody ItemNeed itemNeed) {
        System.out.printf("--------Initialized Name %s\n", itemNeed.getItemName());
        System.out.printf("--------Initialized Type %s\n", itemNeed.getItemType());
        itemService.addItem(itemNeed);
    }

    @GetMapping
    public List<ItemNeed> getAllItem() {
        return itemService.getAllItem();
    } 

    @GetMapping("/wallet")
    public List<Wallet> getWallet() {
        return itemService.getWallet();
    }

    @GetMapping("/{itemId}")
    public List<ItemNeed> getItem(@PathVariable UUID itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping("/bought")
    public List<ItemNeed> getPaidItem() {
        return itemService.getPaidItem();
    }

    @DeleteMapping("/remove/{itemId}")
    public void removeItem(@PathVariable("itemId") UUID itemId) {
        itemService.removeItem(itemId);
    }
    
    @PostMapping("/pay/{itemId}")
    public void payForItem(@PathVariable("itemId") UUID itemId) {
        itemService.payForItem(itemId);
    }
}
