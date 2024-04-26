package com.eterio.postman.alt.repository;

import com.eterio.postman.alt.model.entity.WorkspaceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceRepository extends MongoRepository<WorkspaceEntity, String> {

    Optional<WorkspaceEntity> findByWorkspaceId(String workspaceId);
    Optional<WorkspaceEntity> findByName(String name);

}
