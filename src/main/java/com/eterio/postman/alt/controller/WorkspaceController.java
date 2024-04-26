package com.eterio.postman.alt.controller;

import com.eterio.postman.alt.model.request.workspace.*;
import com.eterio.postman.alt.model.response.workspace.*;
import com.eterio.postman.alt.service.workspace.WorkspaceService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Workspace Management", value = "Workspace Management")
@Slf4j
@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {


    private final WorkspaceService workspaceService;

    @PostMapping("/create")
    public WorkspaceCreateResponse createWorkspace(@RequestHeader String clientToken,
                                          @RequestBody WorkspaceCreateRequest request){
    log.info("Create Workspace for this user {}",request.getName());
    WorkspaceCreateResponse response = workspaceService.createWorkspace(request);
    return response;
    }

    @PutMapping("/edit")
    public WorkspaceEditResponse updateWorkspace(@RequestHeader String clientToken,
                                        @RequestBody WorkspaceEditRequest request){
        log.info("Update Workspace for this user {}", request.getName());
        WorkspaceEditResponse response = workspaceService.updateWorkspace(request);
        return response;

    }

    @GetMapping("/get")
    public WorkspaceGetResponse getWorkspace(@RequestHeader String clientToken,
                                    @RequestBody WorkspaceGetRequest request){
    log.info("Get {} Workspace with {} access", request.getName(), request.getType());
    WorkspaceGetResponse response = workspaceService.getWorkspace(request);
    return response;
    }

    @PostMapping("/add")
    public WorkspaceAddResponse addWorkspace(@RequestHeader String clientToken,
                                    @RequestBody WorkspaceAddRequest request){
    WorkspaceAddResponse response = workspaceService.addWorkspace(request);
    return response;
    }

    @DeleteMapping("/delete")
    public WorkspaceDeleteResponse deleteWorkspace(@RequestHeader String clientToken,
                                          @RequestBody WorkspaceDeleteRequest request){
        log.info("Delete workspace for {}", request.getWorkspaceId());
        WorkspaceDeleteResponse response=workspaceService.deleteWorkspace(request);
        return response;
    }


}
