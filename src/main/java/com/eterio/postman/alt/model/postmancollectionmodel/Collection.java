package com.eterio.postman.alt.model.postmancollectionmodel;

import com.eterio.postman.alt.model.postmancollectionmodel.properties.Event;
import com.eterio.postman.alt.model.postmancollectionmodel.properties.Info;
import com.eterio.postman.alt.model.postmancollectionmodel.properties.Item;
import com.eterio.postman.alt.model.postmancollectionmodel.properties.Variable;
import lombok.Data;

import java.util.List;


@Data
public class Collection {
    private Info info;
    private List<Item> item;
    private List<Event> event;
    private List<Variable> variable;
}
