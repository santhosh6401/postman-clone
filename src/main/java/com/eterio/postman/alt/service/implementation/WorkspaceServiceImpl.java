package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.entity.WorkspaceEntity;
import com.eterio.postman.alt.model.request.workspace.*;
import com.eterio.postman.alt.model.response.workspace.*;
import com.eterio.postman.alt.repository.WorkspaceRepository;
import com.eterio.postman.alt.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository repository;

    @Override
    public WorkspaceCreateResponse createWorkspace(WorkspaceCreateRequest request) {
        WorkspaceEntity entity = new WorkspaceEntity();
        WorkspaceCreateResponse response = new WorkspaceCreateResponse();
        try {
            entity.setName(request.getName());
            entity.setStatus(request.getStatus());
            entity.setType(request.getType());
            entity.setWorkspaceId(UUID.randomUUID().toString());
            repository.save(entity);
            BeanUtils.copyProperties(entity, response);
            response.setResponse("SUCCESS");
            response.setDescription("Workspace Created Successfully");
        }catch(Exception e){
            log.error("ERROR :: Error while create the workspace, Please try again");
            response.setResponse("FAILED");
            response.setDescription("Error while create the workspace, Please try again");
        }
        return response;
    }

    @Override
    public WorkspaceEditResponse updateWorkspace(WorkspaceEditRequest request) {

        Optional<WorkspaceEntity> entity = repository.findByWorkspaceId(request.getWorkspaceId());
        WorkspaceEditResponse response = new WorkspaceEditResponse();
        if (entity.isPresent()) {
            entity.get().setName(request.getName());
            entity.get().setType(request.getType());
            entity.get().setStatus(request.getStatus());
            repository.save(entity.get());

            BeanUtils.copyProperties(entity.get(), response);
            response.setWorkspaceId(request.getWorkspaceId());
            response.setResponse("SUCCESS");
            response.setDescription("Workspace Updated Successfully");
        } else {
            log.error("ERROR :: WorkspaceId is not present, Please provide valid workspaceId");
            response.setResponse("FAILED");
            response.setDescription("WorkspaceId is not present, Try with valid Id");
        }
        return response;
    }

    @Override
    public WorkspaceGetResponse getWorkspace(WorkspaceGetRequest request){
        WorkspaceGetResponse response = new WorkspaceGetResponse();
        WorkspaceGet workspaceGet= new WorkspaceGet();
        List<WorkspaceGet> workspaceGetList=new ArrayList<>();
        Optional<WorkspaceEntity> entity= repository.findByName(request.getName());

        if(entity.isPresent()){
            workspaceGet.setId(entity.get().getWorkspaceId());
            workspaceGet.setName(entity.get().getName());
            workspaceGet.setStatus(entity.get().getStatus());
            workspaceGetList.add(workspaceGet);
            response.setWorkspaces(workspaceGetList);
            response.setResponse("SUCCESS");
            response.setDescription("Workspace Retrived");
        }else{
            log.error("ERROR :: Workspace Name is not present, Please provide valid Name");
            response.setResponse("FAILED");
            response.setDescription("Workspace Name is not present, Try with valid name");
        }
        return response;
    }

    @Override
    public WorkspaceAddResponse addWorkspace(WorkspaceAddRequest request){
        Optional<WorkspaceEntity> entity= repository.findByWorkspaceId(request.getWorkspaceId());
        WorkspaceAddResponse response = new WorkspaceAddResponse();
        if(entity.isPresent()){
            response.setResponse("SUCCESS");
            response.setDescription("Workspace Added Successfully");
        }else{
            log.error("ERROR :: Error while add the workspace in the workspaceId {}", request.getWorkspaceId());
            response.setResponse("FAILED");
            response.setDescription("Error while add workspace");
        }
        return response;
    }

    @Override
    public WorkspaceDeleteResponse deleteWorkspace(WorkspaceDeleteRequest request){
        WorkspaceDeleteResponse response = new WorkspaceDeleteResponse();
       try {
           repository.deleteById(request.getWorkspaceId());
           response.setResponse("SUCCESS");
           response.setDescription("Workspace Deleted Successfully");
       }catch (Exception e){
           log.error("ERROR :: Error while delete the workspace");
           response.setResponse("FAILED");
           response.setDescription("Workspace not deleted successfully");
       }
       return response;
    }



}
