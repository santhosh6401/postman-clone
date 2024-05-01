package com.eterio.postman.alt.repository;

import com.eterio.postman.alt.model.entity.ProjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<ProjectEntity, String> {

    Optional<ProjectEntity> findByName(String name);

    Optional<ProjectEntity> findByProjectId(String projectId);

    Optional<ProjectEntity> findByGitProjectId(String gitProjectId);

    Optional<ProjectEntity> findByProjectPath(String projectPath);
}
