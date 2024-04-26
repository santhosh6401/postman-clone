package com.eterio.postman.alt.model.request.workspace;

import com.eterio.postman.alt.model.enums.workspace.WorkspaceAccess;
import com.eterio.postman.alt.model.enums.workspace.WorkspaceStatus;
import com.eterio.postman.alt.model.enums.workspace.WorkspaceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceGetRequest {

    private WorkspaceAccess type;
    private String name;
}
