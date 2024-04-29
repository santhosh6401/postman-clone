package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.request.workspace.*;
import com.eterio.postman.alt.model.response.workspace.*;
import org.springframework.stereotype.Service;

@Service
public interface WorkspaceService {

    public WorkspaceCreateResponse createWorkspace(WorkspaceCreateRequest request);
    public WorkspaceEditResponse updateWorkspace(WorkspaceEditRequest request);
    public WorkspaceGetResponse getWorkspace(WorkspaceGetRequest request);
    public WorkspaceDeleteResponse deleteWorkspace(WorkspaceDeleteRequest request);
    public WorkspaceAddResponse addWorkspace(WorkspaceAddRequest request);
}
