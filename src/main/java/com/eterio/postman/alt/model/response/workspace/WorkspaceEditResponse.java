package com.eterio.postman.alt.model.response.workspace;

import com.eterio.postman.alt.model.enums.workspace.WorkspaceStatus;
import com.eterio.postman.alt.model.enums.workspace.WorkspaceType;
import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceEditResponse extends CommonResponse {
    private String workspaceId;
    private String name;
    private WorkspaceType type;
    private WorkspaceStatus status;
}
