package com.example.demo.service;

import com.example.demo.dao.FavoriteDao;
import com.example.demo.entity.db.Item;
import com.example.demo.entity.db.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteDao favoriteDao;

    public void setFavoriteItem(String userId, Item item){
        favoriteDao.setFavoriteItem(userId, item);
    }
    public void unsetFavoriteItem(String userId, String itemId){
        favoriteDao.unsetFavoriteItem(userId, itemId);
    }
    public Map<String, List<Item>> getFavoriteItems (String userId){
        Map<String, List<Item>> itemMap = new HashMap<>();
        for(ItemType type: ItemType.values()){
            itemMap.put(type.toString(), new ArrayList<>());
        }
        Set<Item> favorites = favoriteDao.getFavoriteItem(userId);
        for(Item item : favorites){
            itemMap.get(item.getType().toString()).add(item);
        }
        return itemMap;
    }
}
