package com.eterio.postman.alt.controller;

import com.eterio.postman.alt.model.request.workspace.*;
import com.eterio.postman.alt.model.response.workspace.*;
import com.eterio.postman.alt.service.WorkspaceService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Workspace API", value = "Workspace API")
@Slf4j
@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {              // TODO client token validation ...


    private final WorkspaceService workspaceService;

    @PostMapping("/create")
    public WorkspaceCreateResponse createWorkspace(@RequestHeader String uniqueInteractionId,
                                                   @RequestHeader String clientToken,
                                                   @RequestBody WorkspaceCreateRequest request) {
        log.info("interactionId :: [{}] create workspace for this user {}", uniqueInteractionId, request.getName());
        return workspaceService.createWorkspace(request, uniqueInteractionId);
    }

    @PutMapping("/edit")
    public WorkspaceEditResponse updateWorkspace(@RequestHeader String uniqueInteractionId,
                                                 @RequestHeader String clientToken,
                                                 @RequestBody WorkspaceEditRequest request) {
        log.info("interactionId :: [{}] update the workspace : {} ", uniqueInteractionId, request.getName());
        return workspaceService.updateWorkspace(request, uniqueInteractionId);
    }

    @GetMapping("/get")
    public WorkspaceGetResponse getWorkspace(@RequestHeader String uniqueInteractionId,
                                             @RequestHeader String clientToken,
                                             @RequestBody WorkspaceGetRequest request) {
        log.info("interactionId : [{}] get {} workspace with {} access", uniqueInteractionId, request.getName(), request.getType());
        return workspaceService.getWorkspace(request, uniqueInteractionId);

    }

    @PostMapping("/add")
    public WorkspaceAddResponse addWorkspace(@RequestHeader String uniqueInteractionId,
                                             @RequestHeader String clientToken,
                                             @RequestBody WorkspaceAddRequest request) {
        log.info("interactionId : [{}] add workspace", uniqueInteractionId);
        return workspaceService.addWorkspace(request, uniqueInteractionId);
    }

    @DeleteMapping("/delete")
    public WorkspaceDeleteResponse deleteWorkspace(@RequestHeader String uniqueInteractionId,
                                                   @RequestHeader String clientToken,
                                                   @RequestBody WorkspaceDeleteRequest request) {
        log.info("interactionId :: [{}] delete workspace for {}", uniqueInteractionId, request.getWorkspaceId());
        return workspaceService.deleteWorkspace(request, uniqueInteractionId);
    }


}
