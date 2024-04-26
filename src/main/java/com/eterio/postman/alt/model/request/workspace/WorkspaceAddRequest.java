package com.eterio.postman.alt.model.request.workspace;

import com.eterio.postman.alt.model.enums.workspace.WorkspaceAccess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceAddRequest {
    private String workspaceId;
}
