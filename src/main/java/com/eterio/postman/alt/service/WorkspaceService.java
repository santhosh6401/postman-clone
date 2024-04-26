package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.request.workspace.*;
import com.eterio.postman.alt.model.response.workspace.*;
import org.springframework.stereotype.Service;

@Service
public interface WorkspaceService {

    public WorkspaceCreateResponse createWorkspace(WorkspaceCreateRequest request, String uniqueInteractionId);
    public WorkspaceEditResponse updateWorkspace(WorkspaceEditRequest request, String uniqueInteractionId);
    public WorkspaceGetResponse getWorkspace(WorkspaceGetRequest request, String uniqueInteractionId);
    public WorkspaceDeleteResponse deleteWorkspace(WorkspaceDeleteRequest request, String uniqueInteractionId);
    public WorkspaceAddResponse addWorkspace(WorkspaceAddRequest request, String uniqueInteractionId);
}
