package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.entity.ProjectEntity;
import com.eterio.postman.alt.model.request.project.*;
import com.eterio.postman.alt.model.response.project.*;
import com.eterio.postman.alt.repository.ProjectRepository;
import com.eterio.postman.alt.service.ProjectService;
import com.eterio.postman.alt.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.eterio.postman.alt.constant.AppConstant.FAILED;
import static com.eterio.postman.alt.constant.AppConstant.SUCCESS;

@Service
@Slf4j
@RequiredArgsConstructor

public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final HelperUtils helperUtils;

    @Override
    public ProjectCreateResponse createProject(ProjectCreateRequest request, String uniqueInteractionId) {
        ProjectEntity entity = new ProjectEntity();
        ProjectCreateResponse response = new ProjectCreateResponse();
        try {

            Optional<ProjectEntity> projectEntityByName = repository.findByName(request.getName());
            Optional<ProjectEntity> projectEntityByGitProjectId = repository.findByGitProjectId(request.getGitProjectId());
            Optional<ProjectEntity> projectEntityByPath = repository.findByProjectPath(request.getProjectPath());


            if (projectEntityByName.isPresent() || projectEntityByGitProjectId.isPresent() || projectEntityByPath.isPresent()) {
                response.setResponse(FAILED);
                response.setDescription("Project details already exist....");
                return response;
            }

            entity.setProjectId(helperUtils.generateId("GP"));
            entity.setName(request.getName());
            entity.setProjectPath(request.getProjectPath());
            entity.setGitProjectId(request.getGitProjectId());
            entity.setAudit(helperUtils.createAudit(uniqueInteractionId));
            entity.setStatusLifeCycle(helperUtils.upsertLifeCycles("project created", new ArrayList<>()));

            repository.save(entity);
            BeanUtils.copyProperties(entity, response);
            response.setResponse(SUCCESS);
            response.setDescription("Project Created Successfully");
        } catch (Exception e) {
            log.error("interactionId : [{}] :: error while create the Project, errorMsg : {} ", uniqueInteractionId, e.getMessage());
            response.setResponse(FAILED);
            response.setDescription("Error while create the Project, Please try again");
        }
        return response;
    }

    @Override
    public ProjectEditResponse updateProject(ProjectEditRequest request, String uniqueInteractionId) {

        Optional<ProjectEntity> entityOptional = repository.findByProjectId(request.getProjectId());
        ProjectEditResponse response = new ProjectEditResponse();
        if (entityOptional.isPresent()) {
            ProjectEntity entity = entityOptional.get();

            if (Objects.nonNull(request.getName()))
                entity.setName(request.getName());
            if (Objects.nonNull(request.getProjectPath()))
                entity.setProjectPath(request.getProjectPath());
            if (Objects.nonNull(request.getGitLabProjectId()))
                entity.setGitProjectId(request.getGitLabProjectId());
            entity.setStatusLifeCycle(helperUtils.upsertLifeCycles("update the Project " + uniqueInteractionId, entity.getStatusLifeCycle()));
            entity.setAudit(helperUtils.updateAudit(uniqueInteractionId, entity.getAudit()));
            repository.save(entity);

            BeanUtils.copyProperties(entityOptional.get(), response);
            response.setProjectId(request.getProjectId());
            response.setResponse(SUCCESS);
            response.setDescription("Project Updated Successfully");
        } else {
            log.error("interactionId : [{}] :: ProjectId is not present, please provide valid ProjectId", uniqueInteractionId);
            response.setResponse(FAILED);
            response.setDescription("ProjectId is not present, try with valid id");
        }
        return response;
    }

    @Override
    public ProjectGetResponse getProject(ProjectGetRequest request, String uniqueInteractionId) {     // TODO :: query method to get the values using profile ...
        ProjectGetResponse response = new ProjectGetResponse();
        ProjectGet ProjectGet = new ProjectGet();
        List<ProjectGet> ProjectGetList = new ArrayList<>();
        if (Objects.nonNull(request.getName())) {
            Optional<ProjectEntity> entity = repository.findByName(request.getName());
            if (entity.isPresent()) {
                ProjectGet.setId(entity.get().getProjectId());
                ProjectGet.setName(entity.get().getName());
                ProjectGetList.add(ProjectGet);
                response.setProjects(ProjectGetList);
                response.setResponse(SUCCESS);
                response.setDescription("Project retrieved");
                return response;
            }
        }

        List<ProjectEntity> profileEntities = repository.findAll();

        if (!profileEntities.isEmpty()) {
            for (ProjectEntity project : profileEntities) {
                ProjectGet getProject = new ProjectGet();
                getProject.setId(project.getProjectId());
                getProject.setName(project.getName());
                ProjectGetList.add(getProject);
            }
            response.setProjects(ProjectGetList);
            response.setResponse(SUCCESS);
            response.setDescription("Project retrieved");
        } else {
            log.error("interactionId : [{}] :: Project name is not present, please provide valid name", uniqueInteractionId);
            response.setResponse(FAILED);
            response.setDescription("Project name is not present, try with valid name");
        }
        return response;
    }

    @Override
    public ProjectDeleteResponse deleteProject(ProjectDeleteRequest request, String uniqueInteractionId) {
        ProjectDeleteResponse response = new ProjectDeleteResponse();
        try {
            repository.deleteById(request.getProjectId());
            response.setResponse(SUCCESS);
            response.setDescription("Project deleted successfully");
        } catch (Exception e) {
            log.error("interactionId : [{}] :: error while delete the Project ", uniqueInteractionId);
            response.setResponse(FAILED);
            response.setDescription("Project not deleted successfully");
        }
        return response;
    }
}
