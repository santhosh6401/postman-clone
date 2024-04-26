package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.collection.Collection;
import com.eterio.postman.alt.model.collection.response.CollectionResponse;
import com.eterio.postman.alt.model.common.CommonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public interface CollectionService {
    CollectionResponse importCollection(String uniqueInteractionId, String workspaceId, MultipartFile file);

    Collection export(String uniqueInteractionId, String workspaceId, String collectionId) throws JsonProcessingException;
}
