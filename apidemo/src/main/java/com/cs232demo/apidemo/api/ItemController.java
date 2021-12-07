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
        itemService.addItem(itemNeed);
    }

    @GetMapping
    public List<ItemNeed> getAllItem() {
        return itemService.getAllItem();
    } 

    @GetMapping("/{itemId}")
    public List<ItemNeed> getItem(@PathVariable UUID itemId) {
        return itemService.getItem(itemId);
    }

    @PostMapping("/remove/{itemId}")
    public void removeItem(@PathVariable("itemId") UUID itemId) {
        itemService.removeItem(itemId);
    }
    
}
