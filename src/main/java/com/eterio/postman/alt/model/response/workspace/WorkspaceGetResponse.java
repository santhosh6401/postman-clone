package com.eterio.postman.alt.model.response.workspace;

import com.eterio.postman.alt.model.enums.workspace.WorkspaceStatus;
import com.eterio.postman.alt.model.enums.workspace.WorkspaceType;
import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceGetResponse extends CommonResponse {
    private List<WorkspaceGet> workspaces;
}
