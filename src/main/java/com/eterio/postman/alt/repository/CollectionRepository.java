package com.eterio.postman.alt.repository;

import com.eterio.postman.alt.model.entity.CollectionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectionRepository extends MongoRepository<CollectionEntity , String> {
    Optional<CollectionEntity> findByCollectionIdAndWorkspaceId(String collectionId, String workspaceId);
}
