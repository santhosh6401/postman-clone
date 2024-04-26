package com.eterio.postman.alt.model.entity;

import com.eterio.postman.alt.model.common.Audit;
import com.eterio.postman.alt.model.common.StatusLifeCycle;
import com.eterio.postman.alt.model.common.workspace.IdName;
import com.eterio.postman.alt.model.enums.workspace.WorkspaceStatus;
import com.eterio.postman.alt.model.enums.workspace.WorkspaceType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "workspace")
public class WorkspaceEntity {
    @Id
    private String workspaceId;  // unique Id
    private String name;
    private String creator;     // save the creator profile
    private List<IdName> collections;
    private WorkspaceType type;
    private WorkspaceStatus status;
    private List<StatusLifeCycle> statusLifeCycle;
    private Audit audit;
}
