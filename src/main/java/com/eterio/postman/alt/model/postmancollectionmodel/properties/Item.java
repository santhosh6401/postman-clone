package com.eterio.postman.alt.model.postmancollectionmodel.properties;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private String itemId;
    private String name;
    private ProtocolProfileBehavior protocolProfileBehavior;
    private List<Item> item;
    private Request request;
    private List<Object> response;
    private List<Event> event;
}
