package com.eterio.postman.alt.model.entity;

import com.eterio.postman.alt.model.collection.Collection;
import com.eterio.postman.alt.model.common.Audit;
import com.eterio.postman.alt.model.common.StatusLifeCycle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "collection")
public class CollectionEntity extends Collection {
    @Id
    private String collectionId;  // unique Id
    private String name;
    private String workspaceId;
    private List<StatusLifeCycle> statusLifeCycle;
    private Audit audit;
}
