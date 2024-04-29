package com.eterio.postman.alt.repository;

import com.eterio.postman.alt.model.entity.ProfileEntity;
import com.eterio.postman.alt.model.entity.WorkspaceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByProfileId(String profileId);
    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);

    Optional<ProfileEntity> findByEmail(String email);

}
