package com.eterio.postman.alt.model.collection;

import com.eterio.postman.alt.model.collection.properties.Event;
import com.eterio.postman.alt.model.collection.properties.Info;
import com.eterio.postman.alt.model.collection.properties.Item;
import com.eterio.postman.alt.model.collection.properties.Variable;
import lombok.Data;

import java.util.List;


@Data
public class Collection {
    private Info info;
    private List<Item> item;
    private List<Event> event;
    private List<Variable> variable;
}
