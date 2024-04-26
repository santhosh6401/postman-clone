package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.response.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CollectionService {
    CommonResponse importCollection(String uniqueInteractionId, String workspaceId, MultipartFile file);
}
