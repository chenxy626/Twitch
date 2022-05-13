package com.example.demo.entity.request;

import com.example.demo.entity.db.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoriteRequestBody {
    private final Item item;

    @JsonCreator
    public FavoriteRequestBody(@JsonProperty("favorite") Item item){
        this.item = item;
    }

    public Item getFavoriteItem(){
        return item;
    }
}
