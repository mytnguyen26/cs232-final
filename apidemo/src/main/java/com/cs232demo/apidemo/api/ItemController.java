package com.cs232demo.apidemo.api;

import com.cs232demo.apidemo.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.cs232demo.apidemo.model.*;

@RequestMapping("api/v1/Item")
@RestController
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
    
}
