package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.request.project.ProjectCreateRequest;
import com.eterio.postman.alt.model.request.project.ProjectDeleteRequest;
import com.eterio.postman.alt.model.request.project.ProjectEditRequest;
import com.eterio.postman.alt.model.request.project.ProjectGetRequest;
import com.eterio.postman.alt.model.response.project.ProjectCreateResponse;
import com.eterio.postman.alt.model.response.project.ProjectDeleteResponse;
import com.eterio.postman.alt.model.response.project.ProjectEditResponse;
import com.eterio.postman.alt.model.response.project.ProjectGetResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {

    public ProjectCreateResponse createProject(ProjectCreateRequest request, String uniqueInteractionId);
    public ProjectEditResponse updateProject(ProjectEditRequest request, String uniqueInteractionId);
    public ProjectGetResponse getProject(ProjectGetRequest request, String uniqueInteractionId);
    public ProjectDeleteResponse deleteProject(ProjectDeleteRequest request, String uniqueInteractionId);

}
