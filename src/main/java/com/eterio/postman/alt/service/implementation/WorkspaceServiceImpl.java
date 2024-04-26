package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.entity.WorkspaceEntity;
import com.eterio.postman.alt.model.request.workspace.*;
import com.eterio.postman.alt.model.response.workspace.*;
import com.eterio.postman.alt.repository.WorkspaceRepository;
import com.eterio.postman.alt.service.WorkspaceService;
import com.eterio.postman.alt.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eterio.postman.alt.constant.AppConstant.FAILED;
import static com.eterio.postman.alt.constant.AppConstant.SUCCESS;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {  //TODO :: need to add the customer Id extract from client token ...

    private final WorkspaceRepository repository;
    private final HelperUtils helperUtils;

    @Override
    public WorkspaceCreateResponse createWorkspace(WorkspaceCreateRequest request, String uniqueInteractionId) {
        WorkspaceEntity entity = new WorkspaceEntity();
        WorkspaceCreateResponse response = new WorkspaceCreateResponse();
        try {
            entity.setWorkspaceId(helperUtils.generateId("WS"));
            entity.setName(request.getName());
            entity.setCreator("");
            entity.setStatus(request.getStatus());
            entity.setType(request.getType());
            entity.setAudit(helperUtils.createAudit(uniqueInteractionId));
            entity.setStatusLifeCycle(helperUtils.upsertLifeCycles("workspace created" , new ArrayList<>()));

            repository.save(entity);
            BeanUtils.copyProperties(entity, response);
            response.setResponse(SUCCESS);
            response.setDescription("Workspace Created Successfully");
        } catch (Exception e) {
            log.error("interactionId : [{}] :: error while create the workspace, errorMsg : {} " , uniqueInteractionId , e.getMessage());
            response.setResponse(FAILED);
            response.setDescription("Error while create the workspace, Please try again");
        }
        return response;
    }

    @Override
    public WorkspaceEditResponse updateWorkspace(WorkspaceEditRequest request, String uniqueInteractionId) {

        Optional<WorkspaceEntity> entityOptional = repository.findByWorkspaceId(request.getWorkspaceId());
        WorkspaceEditResponse response = new WorkspaceEditResponse();
        if (entityOptional.isPresent()) {
            WorkspaceEntity entity = entityOptional.get();
            entity.setName(request.getName());
            entity.setType(request.getType());
            entity.setStatus(request.getStatus());
            entity.setStatusLifeCycle(helperUtils.upsertLifeCycles("update the workspace "+uniqueInteractionId ,entity.getStatusLifeCycle() ));
            entity.setAudit(helperUtils.updateAudit(uniqueInteractionId , entity.getAudit()));
            repository.save(entity);

            BeanUtils.copyProperties(entityOptional.get(), response);
            response.setWorkspaceId(request.getWorkspaceId());
            response.setResponse(SUCCESS);
            response.setDescription("Workspace Updated Successfully");
        } else {
            log.error("interactionId : [{}] :: workspaceId is not present, please provide valid workspaceId" , uniqueInteractionId);
            response.setResponse(FAILED);
            response.setDescription("workspaceId is not present, try with valid id");
        }
        return response;
    }

    @Override
    public WorkspaceGetResponse getWorkspace(WorkspaceGetRequest request, String uniqueInteractionId) {     // TODO :: query method to get the values using profile ...
        WorkspaceGetResponse response = new WorkspaceGetResponse();
        WorkspaceGet workspaceGet = new WorkspaceGet();
        List<WorkspaceGet> workspaceGetList = new ArrayList<>();
        Optional<WorkspaceEntity> entity = repository.findByName(request.getName());

        if (entity.isPresent()) {
            workspaceGet.setId(entity.get().getWorkspaceId());
            workspaceGet.setName(entity.get().getName());
            workspaceGet.setStatus(entity.get().getStatus());
            workspaceGetList.add(workspaceGet);
            response.setWorkspaces(workspaceGetList);
            response.setResponse(SUCCESS);
            response.setDescription("workspace retrieved");
        } else {
            log.error("interactionId : [{}] :: workspace name is not present, please provide valid name" , uniqueInteractionId);
            response.setResponse(FAILED);
            response.setDescription("workspace name is not present, try with valid name");
        }
        return response;
    }

    @Override
    public WorkspaceAddResponse addWorkspace(WorkspaceAddRequest request, String uniqueInteractionId) {         // TODO :: add the workspace Id in profile
        Optional<WorkspaceEntity> entity = repository.findByWorkspaceId(request.getWorkspaceId());
        WorkspaceAddResponse response = new WorkspaceAddResponse();
        if (entity.isPresent()) {
            response.setResponse(SUCCESS);
            response.setDescription("Workspace Added Successfully");
        } else {
            log.error("interactionId : [{}] :: error while add the workspace in the workspaceId {}", uniqueInteractionId , request.getWorkspaceId());
            response.setResponse(FAILED);
            response.setDescription("Error while add workspace");
        }
        return response;
    }

    @Override
    public WorkspaceDeleteResponse deleteWorkspace(WorkspaceDeleteRequest request, String uniqueInteractionId) {
        WorkspaceDeleteResponse response = new WorkspaceDeleteResponse();
        try {
            repository.deleteById(request.getWorkspaceId());
            response.setResponse(SUCCESS);
            response.setDescription("workspace deleted successfully");
        } catch (Exception e) {
            log.error("interactionId : [{}] :: error while delete the workspace " , uniqueInteractionId);
            response.setResponse(FAILED);
            response.setDescription("Workspace not deleted successfully");
        }
        return response;
    }


}
