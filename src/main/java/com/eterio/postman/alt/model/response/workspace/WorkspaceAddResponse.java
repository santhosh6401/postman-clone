package com.eterio.postman.alt.model.response.workspace;

import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceAddResponse extends CommonResponse {
    private List<WorkspaceAdd> workspaces;
}
