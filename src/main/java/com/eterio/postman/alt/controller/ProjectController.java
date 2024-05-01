package com.eterio.postman.alt.controller;

import com.eterio.postman.alt.model.request.project.*;
import com.eterio.postman.alt.model.response.project.*;
import com.eterio.postman.alt.service.ProjectService;
import com.eterio.postman.alt.utils.GenerateAndValidateToken;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static javax.management.remote.JMXConnectionNotification.FAILED;

@Api(tags = "Project Management", value = "Project Management")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    private final GenerateAndValidateToken tokenUtils;

    @PostMapping
    public ProjectCreateResponse createProject(@RequestHeader String uniqueInteractionId,
                                                 @RequestHeader String clientToken,
                                                 @RequestBody ProjectCreateRequest request) {
        log.info("interactionId :: [{}] create workspace for this user {}", uniqueInteractionId, request.getName());

        ProjectCreateResponse response = new ProjectCreateResponse();

        if(Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)){
            response.setResponse(FAILED);
            response.setDescription("token invalid ...:(");
            return response;
        }

        return projectService.createProject(request, uniqueInteractionId);
    }

    @PutMapping
    public ProjectEditResponse updateProject(@RequestHeader String uniqueInteractionId,
                                               @RequestHeader String clientToken,
                                               @RequestBody ProjectEditRequest request) {

        log.info("interactionId :: [{}] update the workspace : {} ", uniqueInteractionId, request.getName());

        ProjectEditResponse response = new ProjectEditResponse();

        if(Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)){
            response.setResponse(FAILED);
            response.setDescription("token invalid ....:(");
            return response;
        }

        return projectService.updateProject(request, uniqueInteractionId);
    }

    @PostMapping("/search")
    public ProjectGetResponse getProject(@RequestHeader String uniqueInteractionId,
                                           @RequestHeader String clientToken,
                                           @RequestBody ProjectGetRequest request) {
        log.info("interactionId : [{}] get {} workspace ", uniqueInteractionId, request.getName());

        ProjectGetResponse response = new ProjectGetResponse();

        if(Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)){
            response.setResponse(FAILED);
            response.setDescription("token invalid ...:(");
            return response;
        }

        return projectService.getProject(request, uniqueInteractionId);

    }

    @Deprecated
    @DeleteMapping
    public ProjectDeleteResponse deleteProject(@RequestHeader String uniqueInteractionId,
                                                 @RequestHeader String clientToken,
                                                 @RequestBody ProjectDeleteRequest request) {

        ProjectDeleteResponse response = new ProjectDeleteResponse();

        if(Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)){
            response.setResponse(FAILED);
            response.setDescription("token invalid ...:(");
            return response;
        }

        log.info("interactionId :: [{}] delete workspace for {}", uniqueInteractionId, request.getProjectId());
        return projectService.deleteProject(request, uniqueInteractionId);
    }


}
