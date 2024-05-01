package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.request.collection.CollectionRequest;
import com.eterio.postman.alt.model.response.CommonResponse;
import com.eterio.postman.alt.model.response.collection.CollectionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface CollectionService {
    ByteArrayResource importCollection(String uniqueInteractionId, String workspaceId, String collectionId) throws JsonProcessingException;


    CollectionResponse createCollection(CollectionRequest request, String uniqueInteractionId, String profileId);

    CollectionResponse updateCollection(CollectionRequest request, String collectionId, String uniqueInteractionId);

    CommonResponse deleteCollection(String collectionId, String uniqueInteractionId, String profileId);

    List<CollectionResponse> getCollection(String projectId, String collectionId, String version, String name, String uniqueInteractionId);
}
