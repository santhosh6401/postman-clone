package com.eterio.postman.alt.model.response.profile;

import com.eterio.postman.alt.model.common.CommonResponse;
import com.eterio.postman.alt.model.common.IdName;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDashboardResponse extends CommonResponse {
    private String firstName;
    private String lastName;
    private String email;
    private List<IdName> workspaces;
    private boolean hasNext;
    private boolean hasPrevious;
}
