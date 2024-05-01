package com.eterio.postman.alt.model.entity;

import com.eterio.postman.alt.model.common.Audit;
import com.eterio.postman.alt.model.common.StatusLifeCycle;
import com.eterio.postman.alt.model.postmancollectionmodel.Collection;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "collection")
public class CollectionEntity extends Collection {
    @Id
    private String collectionId;  // unique Id
    private String name;
    private String projectId;
    private String creator;
    private String filepath;
    private String version;
    private String lastCommitId;        // TODO pending ...
    private List<StatusLifeCycle> statusLifeCycle = new ArrayList<>();
    private Audit audit;
}
