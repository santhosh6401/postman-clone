package com.eterio.postman.alt.model.request.profile;

import lombok.Data;

@Data
public class ApproveWorkspaceRequest {
    private String clientToken;
    private String workspaceId;
    private String approverId;

}
