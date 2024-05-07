package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.request.collection.CollectionRequest;
import com.eterio.postman.alt.model.response.CommonResponse;
import com.eterio.postman.alt.model.response.collection.CollectionResponse;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.TreeItem;

import java.util.List;

public interface CollectionService {
    RepositoryFile importCollection(String projectId, String collectionId , String version ,String profileId , String uniqueInteractionId) throws GitLabApiException;


    CollectionResponse createCollection(CollectionRequest request, String uniqueInteractionId, String profileId);

    CollectionResponse updateCollection(CollectionRequest request, String collectionId, String uniqueInteractionId);

    CommonResponse deleteCollection(String collectionId, String uniqueInteractionId, String profileId);

    List<CollectionResponse> getCollection(String projectId, String collectionId, String version, String name, String uniqueInteractionId);

    List<TreeItem> getFolders(String filePath, String path, String interactionId) throws GitLabApiException;

    RepositoryFile getFile(String filePath, String uniqueInteractionId) throws GitLabApiException;
}
