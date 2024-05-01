package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.entity.CollectionEntity;
import com.eterio.postman.alt.model.request.collection.CollectionRequest;
import com.eterio.postman.alt.model.response.CommonResponse;
import com.eterio.postman.alt.model.response.collection.CollectionResponse;
import com.eterio.postman.alt.repository.CollectionRepository;
import com.eterio.postman.alt.service.CollectionService;
import com.eterio.postman.alt.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.eterio.postman.alt.constant.AppConstant.SUCCESS;
import static javax.management.remote.JMXConnectionNotification.FAILED;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository repository;
    private final HelperUtils helperUtils;

    private final MongoTemplate mongoTemplate;

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

            if(Objects.nonNull(request.getName()))
                entity.setName(request.getName());
            if(Objects.nonNull(request.getFilepath()))
                entity.setFilepath(request.getFilepath());
            if(Objects.nonNull(request.getVersion()))
                entity.setVersion(request.getVersion());
            if(Objects.nonNull(request.getProjectId()))
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
    public ByteArrayResource importCollection(String uniqueInteractionId, String workspaceId, String collectionId) {

        try {

            // TODO :: Git Lab logic

        } catch (Exception ex) {
            log.error("interactionId : [{}] :: errorMsg : {} ", uniqueInteractionId, ex.getMessage());
            throw ex;
        }
        return null;
    }
}
