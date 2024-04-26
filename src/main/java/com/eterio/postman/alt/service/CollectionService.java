package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.collection.response.CollectionResponse;
import com.eterio.postman.alt.model.common.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CollectionService {
    CollectionResponse importCollection(String uniqueInteractionId, String workspaceId, MultipartFile file);
}
