package com.eterio.postman.alt.model.collection;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private String itemId;
    private String name;
    private List<Item> item;
    private Request request;
    private List<Object> response;
}
