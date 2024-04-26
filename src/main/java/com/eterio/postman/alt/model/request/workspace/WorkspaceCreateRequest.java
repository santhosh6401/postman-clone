package com.eterio.postman.alt.model.request.workspace;

import com.eterio.postman.alt.model.enums.workspace.WorkspaceStatus;
import com.eterio.postman.alt.model.enums.workspace.WorkspaceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceCreateRequest {
    private String name;
    private WorkspaceType type;
    private WorkspaceStatus status;
}
