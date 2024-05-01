package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.entity.ProfileEntity;
import com.eterio.postman.alt.model.request.profile.ProfileSignupRequest;
import com.eterio.postman.alt.model.response.profile.GetProfile;
import com.eterio.postman.alt.model.response.profile.GetProfileResponse;
import com.eterio.postman.alt.model.response.profile.ProfileSigninResponse;
import com.eterio.postman.alt.model.response.profile.ProfileSignupResponse;
import com.eterio.postman.alt.repository.ProfileRepository;
import com.eterio.postman.alt.service.ProfileService;
import com.eterio.postman.alt.utils.GenerateAndValidateToken;
import com.eterio.postman.alt.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.eterio.postman.alt.constant.AppConstant.FAILED;
import static com.eterio.postman.alt.constant.AppConstant.SUCCESS;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final HelperUtils helperUtils;
    private final ProfileRepository profileRepository;
    private final MongoTemplate mongoTemplate;

    private final GenerateAndValidateToken tokenService;

    @Override
    public ProfileSignupResponse profileSignup(String interactionId, ProfileSignupRequest request) {

        ProfileEntity entity = new ProfileEntity();
        ProfileSignupResponse response = new ProfileSignupResponse();

        Optional<ProfileEntity> profileEntity = profileRepository.findByEmail(request.getEmail());

        if (profileEntity.isPresent()) {
            response.setResponse(FAILED);
            response.setDescription("sign-up failed already profile exist for this email-id");
            return response;
        }


        entity.setProfileId(helperUtils.generateId("PR"));
        entity.setEmail(request.getEmail());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPassword(helperUtils.encode(request.getPassword()));
        entity.setGitLabAccessToken(helperUtils.encode(request.getGitLabAccessToken()));

        entity.setAudit(helperUtils.createAudit(interactionId));
        profileRepository.save(entity);

        BeanUtils.copyProperties(entity, response);

        response.setClientToken(tokenService.generateToken(entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getProfileId()));

        response.setResponse(SUCCESS);
        response.setDescription("sign-up successfully");

        return response;
    }

    @Override
    public ProfileSigninResponse profileSignIn(String interactionId, String email, String password) {
        Optional<ProfileEntity> entityOptional = profileRepository.findByEmailAndPassword(email, helperUtils.encode(password));
        ProfileSigninResponse response = new ProfileSigninResponse();
        if (entityOptional.isPresent()) {
            ProfileEntity entity = entityOptional.get();
            response.setClientToken(tokenService.generateToken(entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getProfileId()));
            response.setResponse(SUCCESS);
            response.setDescription("sign-in successfully");
        } else {
            response.setResponse(FAILED);
            response.setDescription("profile not found");
        }
        return response;
    }

    @Override
    public GetProfileResponse getProfile(String interactionId, String profileId, String email) {

        GetProfileResponse response = new GetProfileResponse();

        Query query = new Query();

        if (Objects.nonNull(email))
            query.addCriteria(Criteria.where("email").is(email));

        if (Objects.nonNull(profileId) && Objects.isNull(email))
            query.addCriteria(Criteria.where("profileId").is(profileId));


        List<ProfileEntity> profileEntities = mongoTemplate.find(query, ProfileEntity.class);

        if (!profileEntities.isEmpty()) {
            List<GetProfile> profileList = new ArrayList<>();

            for (ProfileEntity entity : profileEntities) {
                GetProfile profile = new GetProfile();
                BeanUtils.copyProperties(entity, profile);
                profileList.add(profile);
            }

            response.setProfiles(profileList);
            response.setResponse(SUCCESS);
            response.setDescription("get the profiles successfully ...");
        } else {
            response.setResponse(FAILED);
            response.setDescription("no profile found ...");
        }

        return response;
    }

    @Override
    public ProfileSignupResponse updateProfile(String interactionId, String profileId, ProfileSignupRequest request) {
        ProfileSignupResponse response = new ProfileSignupResponse();

        Optional<ProfileEntity> entityOptional = profileRepository.findByProfileId(profileId);

        if (entityOptional.isEmpty()) {
            response.setResponse(FAILED);
            response.setDescription("profile not found .... :( ");
            return response;
        }

        ProfileEntity entity = entityOptional.get();

        if (Objects.nonNull(request.getEmail()))
            entity.setEmail(request.getEmail());
        if (Objects.nonNull(request.getFirstName()))
            entity.setFirstName(request.getFirstName());
        if (Objects.nonNull(request.getLastName()))
            entity.setLastName(request.getLastName());
        if (Objects.nonNull(request.getPassword()))
            entity.setPassword(helperUtils.encode(request.getPassword()));
        if (Objects.nonNull(request.getGitLabAccessToken()))
            entity.setGitLabAccessToken(helperUtils.encode(request.getGitLabAccessToken()));

        entity.setAudit(helperUtils.createAudit(interactionId));
        profileRepository.save(entity);

        BeanUtils.copyProperties(entity, response);
        response.setResponse(SUCCESS);
        response.setDescription("profile updated successfully .... :) ");
        response.setClientToken(tokenService.generateToken(entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getProfileId()));

        return response;
    }


}
