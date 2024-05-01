package com.eterio.postman.alt.model.entity;

import com.eterio.postman.alt.model.common.Audit;
import com.eterio.postman.alt.model.common.StatusLifeCycle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "project")
public class ProjectEntity {
    @Id
    private String projectId;  // unique Id
    private String name;
    private String projectPath;
    private String gitProjectId;
    private List<StatusLifeCycle> statusLifeCycle;
    private Audit audit;
}
