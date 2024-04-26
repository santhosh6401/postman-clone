package com.eterio.postman.alt.model.entity;

import com.eterio.postman.alt.model.collection.Event;
import com.eterio.postman.alt.model.collection.Info;
import com.eterio.postman.alt.model.collection.Item;
import com.eterio.postman.alt.model.collection.Variable;
import com.eterio.postman.alt.model.common.Audit;
import com.eterio.postman.alt.model.common.StatusLifeCycle;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "collection")
public class CollectionEntity {
    private String collectionId;  // unique Id
    private String name;
    private String workspaceId;
    private Info info;
    private List<Item> item;
    private List<Event> event;
    private List<Variable> variable;
    private List<StatusLifeCycle> statusLifeCycle;
    private Audit audit;
}
