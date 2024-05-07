package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.entity.CollectionEntity;
import com.eterio.postman.alt.model.entity.ProfileEntity;
import com.eterio.postman.alt.model.entity.ProjectEntity;
import com.eterio.postman.alt.model.request.collection.CollectionRequest;
import com.eterio.postman.alt.model.response.CommonResponse;
import com.eterio.postman.alt.model.response.collection.CollectionResponse;
import com.eterio.postman.alt.repository.CollectionRepository;
import com.eterio.postman.alt.repository.ProfileRepository;
import com.eterio.postman.alt.repository.ProjectRepository;
import com.eterio.postman.alt.service.CollectionService;
import com.eterio.postman.alt.utils.GitLabIntegrationService;
import com.eterio.postman.alt.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.TreeItem;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.eterio.postman.alt.constant.AppConstant.SUCCESS;
import static javax.management.remote.JMXConnectionNotification.FAILED;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository repository;
    private final HelperUtils helperUtils;

    private final MongoTemplate mongoTemplate;

    private final ProfileRepository profileRepository;

    private final ProjectRepository projectRepository;

    private final GitLabIntegrationService gitLabIntegrationService;

    @Override
    public RepositoryFile importCollection(String projectId, String collectionId, String version, String profileId, String uniqueInteractionId) throws GitLabApiException {


        Query query = new Query();

        if (Objects.nonNull(collectionId))
            query.addCriteria(Criteria.where("collectionId").is(collectionId));

        if (Objects.nonNull(version))
            query.addCriteria(Criteria.where("version").is(version));

        if (Objects.nonNull(projectId))
            query.addCriteria(Criteria.where("projectId").is(projectId));


        query.with(Sort.by(Sort.Order.desc("audit.createdDate")));

        List<CollectionEntity> collectionEntities = mongoTemplate.find(query, CollectionEntity.class);

        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByProfileId(profileId);

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findByProjectId(projectId);

        if (profileEntityOptional.isPresent() && collectionEntities.size() == 1 && projectEntityOptional.isPresent()) {

            RepositoryFile repositoryFile = gitLabIntegrationService.getFile(projectEntityOptional.get().getGitProjectId(), helperUtils.decode(profileEntityOptional.get().getGitLabAccessToken()), collectionEntities.get(0).getFilepath());
            if (Objects.nonNull(repositoryFile)) {
                CollectionEntity collectionEntity = collectionEntities.get(0);
                collectionEntity.setLastCommitId(repositoryFile.getLastCommitId());
                return repositoryFile;
            } else {
                log.error("interactionId : [{}] git repo file is null", uniqueInteractionId);
                return null;
            }

        } else {
            log.error("interactionId : [{}] , project  not found || profile not found || collection not found or more than one record  ...", uniqueInteractionId);
            return null;
        }

    }

    @Override
    public CollectionResponse createCollection(CollectionRequest request, String uniqueInteractionId, String profileId) {


        CollectionResponse response = new CollectionResponse();

        Query query = new Query();

        if (Objects.nonNull(request.getFilepath())) {
            query.addCriteria(Criteria.where("filePath").is(request.getFilepath()));
        }
        if (Objects.nonNull(request.getName())) {
            query.addCriteria(Criteria.where("name").is(request.getName()));
        }
        if (Objects.nonNull(request.getVersion())) {
            query.addCriteria(Criteria.where("version").is(request.getVersion()));
        }
        if (Objects.nonNull(request.getProjectId())) {
            query.addCriteria(Criteria.where("projectId").is(request.getProjectId()));
        }

        query.with(Sort.by(Sort.Order.desc("audit.createdDate")));

        List<CollectionEntity> collectionEntities = mongoTemplate.find(query, CollectionEntity.class);

        if (!collectionEntities.isEmpty()) {
            response.setResponse(FAILED);
            response.setDescription("Requested CollectionInfo already exist");
            return response;
        }
        try {
            CollectionEntity collectionEntity = new CollectionEntity();
            BeanUtils.copyProperties(request, collectionEntity);
            collectionEntity.setCreator(profileId);
            collectionEntity.setCollectionId(helperUtils.generateId("CI"));
            collectionEntity.setAudit(helperUtils.createAudit(uniqueInteractionId));
            collectionEntity.setStatusLifeCycle(helperUtils.upsertLifeCycles("job post created ", collectionEntity.getStatusLifeCycle()));
            repository.save(collectionEntity);
            BeanUtils.copyProperties(collectionEntity, response);
            response.setResponse(SUCCESS);
            response.setDescription("collection created successfully");

        } catch (Exception e) {
            response.setResponse(FAILED);
            response.setDescription("Something went wrong...:( " + e.getMessage());

        }

        return response;

    }

    @Override
    public CollectionResponse updateCollection(CollectionRequest request, String collectionId, String uniqueInteractionId) {

        CollectionResponse response = new CollectionResponse();

        Optional<CollectionEntity> entityOptional = repository.findById(collectionId);

        if (entityOptional.isEmpty()) {
            response.setResponse(FAILED);
            response.setDescription("record is not available");
        } else {
            CollectionEntity entity = entityOptional.get();

            if (Objects.nonNull(request.getName()))
                entity.setName(request.getName());
            if (Objects.nonNull(request.getFilepath()))
                entity.setFilepath(request.getFilepath());
            if (Objects.nonNull(request.getVersion()))
                entity.setVersion(request.getVersion());
            if (Objects.nonNull(request.getProjectId()))
                entity.setProjectId(request.getProjectId());

            entity.setAudit(helperUtils.updateAudit(uniqueInteractionId, entity.getAudit()));
            entity.setStatusLifeCycle(helperUtils.upsertLifeCycles("job post  updated", entity.getStatusLifeCycle()));
            repository.save(entity);
            BeanUtils.copyProperties(entity, response);
            response.setResponse(SUCCESS);
            response.setDescription("collection updated successfully ....");
        }

        return response;
    }

    @Override
    public CommonResponse deleteCollection(String collectionId, String uniqueInteractionId, String profileId) {
        CommonResponse response = new CommonResponse();

        Query query = new Query();

        if (Objects.nonNull(collectionId)) {
            query.addCriteria(Criteria.where("collectionId").is(collectionId));
        }
        if (Objects.nonNull(profileId)) {
            query.addCriteria(Criteria.where("creator").is(profileId));
        }

        query.with(Sort.by(Sort.Order.desc("audit.createdDate")));

        List<CollectionEntity> collectionEntities = mongoTemplate.find(query, CollectionEntity.class);

        if (collectionEntities.size() == 1) {
            repository.deleteById(collectionId);
            response.setResponse(SUCCESS);
            response.setDescription("collection deleted successfully...");
        } else if (collectionEntities.size() > 1) {
            response.setResponse(FAILED);
            response.setDescription("more than one record available");
        } else {
            response.setResponse(SUCCESS);
            response.setDescription("no record available | you are not a creator");
        }

        return response;
    }

    @Override
    public List<CollectionResponse> getCollection(String projectId, String collectionId, String version, String name, String uniqueInteractionId) {

        List<CollectionResponse> collectionResponses = new ArrayList<>();


        Query query = new Query();

        if (Objects.nonNull(projectId))
            query.addCriteria(Criteria.where("projectId").is(projectId));

        if (Objects.nonNull(name))
            query.addCriteria(Criteria.where("name").is(name));

        if (Objects.nonNull(collectionId))
            query.addCriteria(Criteria.where("collectionId").is(collectionId));

        if (Objects.nonNull(version))
            query.addCriteria(Criteria.where("version").is(version));


        query.with(Sort.by(Sort.Order.desc("audit.createdDate")));

        List<CollectionEntity> collectionEntities = mongoTemplate.find(query, CollectionEntity.class);

        if (collectionEntities.isEmpty()) {
            return new ArrayList<>();
        }

        for (CollectionEntity entity : collectionEntities) {
            CollectionResponse response = new CollectionResponse();
            BeanUtils.copyProperties(entity, response);
            collectionResponses.add(response);

        }

        return collectionResponses;
    }

    @Override
    public List<TreeItem> getFolders(String filePath, String type, String interactionId) throws GitLabApiException {
        log.info("interactionId :: [{}] , get folders from gitLab ", interactionId);

        try {

            List<TreeItem> treeItems = gitLabIntegrationService.getFolderStructure(filePath);

            TreeItem.Type treeType = type.equalsIgnoreCase("FOLDER") ? TreeItem.Type.TREE : TreeItem.Type.BLOB;

            treeItems = treeItems.stream().filter(treeItem -> treeItem.getType().equals(treeType)).collect(Collectors.toList());

            return !ObjectUtils.isEmpty(treeItems) ? treeItems : new ArrayList<>();

        } catch (Exception e) {
            log.error("interactionId : [{}] errorMsg : {} ", interactionId, e.getMessage());
            throw e;
        }
    }

    @Override
    public RepositoryFile getFile(String filePath, String uniqueInteractionId) throws GitLabApiException {
        log.info("interactionId :: [{}] , get folders from gitLab ", uniqueInteractionId);

        try {

            return gitLabIntegrationService.getFile(filePath);
        } catch (Exception e) {
            log.error("interactionId : [{}] errorMsg : {} ", uniqueInteractionId, e.getMessage());
            throw e;
        }
    }


}
