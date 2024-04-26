package com.eterio.postman.alt.model.response.workspace;

import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceAddResponse extends CommonResponse {
    private List<WorkspaceAdd> workspaces;
}
