package com.eterio.postman.alt.model.response.workspace;

import com.eterio.postman.alt.model.enums.workspace.WorkspaceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceAdd {
    private String id;
    private String name;
}
