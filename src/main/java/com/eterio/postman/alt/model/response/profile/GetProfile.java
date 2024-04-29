package com.eterio.postman.alt.model.response.profile;

import com.eterio.postman.alt.model.common.IdName;
import com.eterio.postman.alt.model.enums.ProfileType;
import com.eterio.postman.alt.model.response.workspace.WorkspaceGet;
import lombok.Data;

import java.util.List;

@Data
public class GetProfile {
    private String firstName;
    private String lastName;
    private String email;
    private List<IdName> workspaces;
}
